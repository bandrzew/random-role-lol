package com.random.role.lol.ddragon.service;

import static java.util.stream.Collectors.toSet;

import com.random.role.lol.champion.model.Champion;
import com.random.role.lol.champion.model.Role;
import com.random.role.lol.champion.repository.ChampionRepository;
import com.random.role.lol.ddragon.client.DdragonClient;
import com.random.role.lol.ddragon.dto.DdragonChampionDto;
import com.random.role.lol.ddragon.resource.DdragonResource;
import com.random.role.lol.profile.model.ProfileToChampion;
import com.random.role.lol.profile.model.ProfileType;
import com.random.role.lol.profile.model.SpecialProfile;
import com.random.role.lol.profile.service.ProfileService;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DdragonService {

	private final ChampionRepository championRepository;

	private final DdragonResource ddragonResource;

	private final ProfileService profileService;

	@Autowired
	public DdragonService(ChampionRepository championRepository, DdragonClient ddragonClient, ProfileService profileService) {
		this.championRepository = championRepository;
		this.ddragonResource = ddragonClient.getDdragonResource();
		this.profileService = profileService;
	}

	public void importChampions() {
		SpecialProfile allChampionsProfile = profileService.getSpecial(ProfileType.ALL_CHAMPIONS).orElseGet(this::createSpecialProfile);
		Set<Integer> existingChampionIds = profileService.listChampions(allChampionsProfile)
				.stream()
				.map(ProfileToChampion::getChampion)
				.map(Champion::getId)
				.collect(toSet());

		ddragonResource.getChampions(getMostRecentVersion())
				.getData()
				.entrySet()
				.stream()
				.map(this::updateChampion)
				.filter(champion -> !existingChampionIds.contains(champion.getId()))
				.forEach(champion -> Role.POSITIONS.forEach(role -> profileService.addChampion(allChampionsProfile, champion, role)));
	}

	private SpecialProfile createSpecialProfile() {
		SpecialProfile specialProfile = new SpecialProfile();
		specialProfile.setProfileType(ProfileType.ALL_CHAMPIONS);
		specialProfile.setRemovalRestricted(true);
		specialProfile.setName(ProfileType.ALL_CHAMPIONS.getName());
		return profileService.save(specialProfile);
	}

	private Champion updateChampion(Map.Entry<String, DdragonChampionDto> nameToChampion) {
		Champion champion = championRepository.findByName(nameToChampion.getKey()).orElseGet(Champion::new);
		champion.setName(nameToChampion.getKey());
		champion.setDisplayName(nameToChampion.getValue().getName());
		champion.setKey(Integer.parseInt(nameToChampion.getValue().getKey()));

		return championRepository.save(champion);
	}

	private String getMostRecentVersion() {
		return Optional.ofNullable(ddragonResource.getVersions())
				.map(List::iterator)
				.filter(Iterator::hasNext)
				.map(Iterator::next)
				.orElse(Strings.EMPTY);
	}

}
