package com.random.role.lol.profile.service;

import com.random.role.lol.champion.model.Champion;
import com.random.role.lol.champion.model.Role;
import com.random.role.lol.common.randomizer.Random;
import com.random.role.lol.profile.model.Profile;
import com.random.role.lol.profile.model.ProfileToChampion;
import com.random.role.lol.profile.model.ProfileType;
import com.random.role.lol.profile.model.SpecialProfile;
import com.random.role.lol.profile.repository.ProfileRepository;
import com.random.role.lol.profile.repository.ProfileToChampionRepository;
import com.random.role.lol.profile.repository.SpecialProfileRepository;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProfileService {

	private final EntityManager em;

	private final ProfileRepository profileRepository;

	private final SpecialProfileRepository specialProfileRepository;

	private final ProfileToChampionRepository profileToChampionRepository;

	@Autowired
	public ProfileService(EntityManager em, ProfileRepository profileRepository, SpecialProfileRepository specialProfileRepository,
			ProfileToChampionRepository profileToChampionRepository) {
		this.em = em;
		this.profileRepository = profileRepository;
		this.specialProfileRepository = specialProfileRepository;
		this.profileToChampionRepository = profileToChampionRepository;
	}

	public List<Profile> list() {
		return profileRepository.findAll();
	}

	public Optional<Profile> get(int id) {
		return Optional.ofNullable(em.find(Profile.class, id));
	}

	public Profile create(String profileName) {
		return save(new Profile(profileName));
	}

	public <T extends Profile> T save(T profile) {
		em.persist(profile);
		return profile;
	}

	public Optional<Profile> edit(int id, Profile inputProfile) {
		return profileRepository.findById(id).map(profile -> {
			profile.setName(inputProfile.getName());
			return profile;
		}).map(profileRepository::save);
	}

	public void delete(Profile profile) {
		profileRepository.delete(profile);
	}

	public Optional<SpecialProfile> getSpecial(ProfileType profileType) {
		return specialProfileRepository.findByProfileType(profileType);
	}

	// TODO: split into 2nd service

	public List<ProfileToChampion> listChampions(Profile profile) {
		return profileToChampionRepository.findAllByProfile(profile);
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
		return get(profileId).filter(profile -> champion != null).map(profile -> {
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
		return Random.collectionElement(champions);
	}

	private Optional<ProfileToChampion> getChampion(ProfileToChampion profileToChampion) {
		return Optional.ofNullable(em.find(Champion.class, profileToChampion.getChampion().getId()))
				.flatMap(champion -> profileToChampionRepository.findByProfileAndChampionAndRole(profileToChampion.getProfile(), champion,
						profileToChampion.getRole()));
	}

}
