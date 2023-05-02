package com.random.role.lol.ddragon.service;

import static com.random.role.lol.common.Collections.isNullOrEmpty;
import static java.util.stream.Collectors.toMap;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.random.role.lol.champion.model.Champion;
import com.random.role.lol.champion.model.Role;
import com.random.role.lol.champion.repository.ChampionRepository;
import com.random.role.lol.ddragon.client.DdragonClient;
import com.random.role.lol.ddragon.dto.DdragonChampionDto;
import com.random.role.lol.ddragon.resource.DdragonResource;
import com.random.role.lol.profile.model.ProfileType;
import com.random.role.lol.profile.model.SpecialProfile;
import com.random.role.lol.profile.service.ProfileChampionService;
import com.random.role.lol.profile.service.ProfileService;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class DdragonService {

	private final static Logger LOGGER = LogManager.getLogger(DdragonService.class);

	private final ChampionRepository championRepository;

	private final DdragonResource ddragonResource;

	private final ProfileService profileService;

	private final ProfileChampionService profileChampionService;

	private final EntityManager em;

	public DdragonService(ChampionRepository championRepository, DdragonClient ddragonClient, ProfileService profileService,
			ProfileChampionService profileChampionService, EntityManager em) {
		this.championRepository = championRepository;
		this.ddragonResource = ddragonClient.getDdragonResource();
		this.profileService = profileService;
		this.profileChampionService = profileChampionService;
		this.em = em;
	}

	@Async
	@Scheduled(cron = "${champion.import.cron}")
	public void importChampions() {
		LOGGER.info("[DDRAGON] Started champion import");
		Map<String, Champion> nameToChampion = championRepository.findAll().stream().collect(toMap(Champion::getName, Function.identity()));
		List<Champion> createdChampions = ddragonResource.getChampions(getMostRecentVersion())
				.getData()
				.entrySet()
				.stream()
				.map(ddragonEntry -> {
					Champion champion = nameToChampion.get(ddragonEntry.getKey());
					return updateChampion(champion, ddragonEntry.getValue(), ddragonEntry.getKey());
				})
				.filter(champion -> !nameToChampion.containsKey(champion.getName()))
				.collect(Collectors.toList());

		fillSpecialProfile(createdChampions);
		LOGGER.info("[DDRAGON] Finished champion import");
	}

	private void fillSpecialProfile(List<Champion> createdChampions) {
		if (isNullOrEmpty(createdChampions))
			return;

		SpecialProfile allChampionsProfile = profileService.getSpecial(ProfileType.ALL_CHAMPIONS).orElseGet(this::createSpecialProfile);
		createdChampions.forEach(
				champion -> Role.POSITIONS.forEach(role -> profileChampionService.addChampion(allChampionsProfile, champion, role)));
	}

	private SpecialProfile createSpecialProfile() {
		SpecialProfile specialProfile = new SpecialProfile();
		specialProfile.setProfileType(ProfileType.ALL_CHAMPIONS);
		specialProfile.setRemovalRestricted(true);
		specialProfile.setName(ProfileType.ALL_CHAMPIONS.getName());
		return profileService.save(specialProfile);
	}

	private Champion updateChampion(Champion champion, DdragonChampionDto ddragonChampion, String name) {
		boolean isNew = champion == null;
		if (isNew) {
			champion = new Champion();
			champion.setName(name);
			em.persist(champion);
		}

		champion.setDisplayName(ddragonChampion.getName());
		champion.setKey(Integer.parseInt(ddragonChampion.getKey()));

		if (!isNew)
			em.merge(champion);

		return champion;
	}

	private String getMostRecentVersion() {
		return Optional.ofNullable(ddragonResource.getVersions())
				.map(List::iterator)
				.filter(Iterator::hasNext)
				.map(Iterator::next)
				.orElse(Strings.EMPTY);
	}

}
