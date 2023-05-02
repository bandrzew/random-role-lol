package com.random.role.lol.profile.service;

import static java.util.Collections.singleton;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.random.role.lol.champion.model.Champion;
import com.random.role.lol.champion.model.Role;
import com.random.role.lol.champion.service.ChampionService;
import com.random.role.lol.common.randomizer.Random;
import com.random.role.lol.profile.model.Profile;
import com.random.role.lol.profile.model.ProfileToChampion;
import com.random.role.lol.profile.model.ProfileType;
import com.random.role.lol.profile.model.SpecialProfile;
import com.random.role.lol.profile.repository.ProfileRepository;
import com.random.role.lol.profile.repository.ProfileToChampionRepository;
import com.random.role.lol.profile.repository.SpecialProfileRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProfileChampionService {

	private final EntityManager em;

	private final ChampionService championService;

	private final ProfileService profileService;

	private final ProfileToChampionRepository profileToChampionRepository;

	public ProfileChampionService(EntityManager em, ChampionService championService, ProfileService profileService,
			ProfileToChampionRepository profileToChampionRepository) {
		this.em = em;
		this.championService = championService;
		this.profileService = profileService;
		this.profileToChampionRepository = profileToChampionRepository;
	}

	public List<ProfileToChampion> listChampions(Profile profile) {
		List<ProfileToChampion> profileChampions = profileToChampionRepository.findAllByProfile(profile);
		championService.fetchChampions(profileChampions);

		return profileChampions;
	}

	public void addChampion(Profile profile, Champion champion, Role role) {
		ProfileToChampion profileToChampion = new ProfileToChampion();
		profileToChampion.setProfile(profile);
		profileToChampion.setChampion(champion);
		profileToChampion.setRole(role);

		profileToChampionRepository.save(profileToChampion);
	}

	public Optional<ProfileToChampion> addChampion(int profileId, ProfileToChampion profileToChampion) {
		Champion champion = em.find(Champion.class, profileToChampion.getChampion().getId());
		return profileService.get(profileId).filter(profile -> champion != null).map(profile -> {
			profileToChampion.setProfile(profile);
			profileToChampion.setChampion(champion);
			//TODO: throw an error if profileToChampion already exists
			getChampion(profileToChampion).orElseGet(() -> profileToChampionRepository.save(profileToChampion));
			return profileToChampion;
		});
	}

	public void removeChampion(ProfileToChampion profileToChampion) {
		getChampion(profileToChampion).ifPresent(profileToChampionRepository::delete);
	}

	public ProfileToChampion getRandomChampion(int id, Role role) {
		List<ProfileToChampion> champions = profileToChampionRepository.findAllByProfileAndRole(id, role);
		ProfileToChampion randomChampion = Random.collectionElement(champions);
		championService.fetchChampions(singleton(randomChampion));

		return randomChampion;
	}

	private Optional<ProfileToChampion> getChampion(ProfileToChampion profileToChampion) {
		return Optional.ofNullable(em.find(Champion.class, profileToChampion.getChampion().getId()))
				.flatMap(champion -> profileToChampionRepository.findByProfileAndChampionAndRole(profileToChampion.getProfile(), champion,
						profileToChampion.getRole()));
	}

}
