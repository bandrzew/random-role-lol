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

	public Profile create(String profileName) {
		return save(new Profile(profileName));
	}

	public <T extends Profile> T save(T profile) {
		em.persist(profile);
		return profile;
	}

	public Optional<Profile> get(int id) {
		return Optional.ofNullable(em.find(Profile.class, id));
	}

	public Optional<Profile> edit(int id, Profile inputProfile) {
		return profileRepository.findById(id).map(profile -> {
			profile.setName(inputProfile.getName());
			return profile;
		}).map(profileRepository::save);
	}

	public boolean delete(int id) {
		Optional<Profile> profile = profileRepository.findById(id);
		profile.ifPresent(profileRepository::delete);
		return profile.isPresent();
	}

	public Optional<SpecialProfile> getSpecial(ProfileType profileType) {
		return specialProfileRepository.findByProfileType(profileType);
	}

	public List<ProfileToChampion> listChampions(int id) {
		return listChampions(em.getReference(Profile.class, id));
	}

	public List<ProfileToChampion> listChampions(Profile profile) {
		return profileToChampionRepository.findAllByProfile(profile);
	}

	public List<ProfileToChampion> listChampionsByRole(int id, Role role) {
		return profileToChampionRepository.findAllByProfileAndRole(em.getReference(Profile.class, id), role);
	}

	public ProfileToChampion addChampion(Profile profile, Champion champion, Role role) {
		ProfileToChampion profileToChampion = new ProfileToChampion();
		profileToChampion.setProfile(profile);
		profileToChampion.setChampion(champion);
		profileToChampion.setRole(role);

		return profileToChampionRepository.save(profileToChampion);
	}

	public ProfileToChampion addChampion(int profileId, int championId, Role role) {
		ProfileToChampion profileToChampion = new ProfileToChampion();
		profileToChampion.setProfile(em.getReference(Profile.class, profileId));
		profileToChampion.setChampion(em.getReference(Champion.class, championId));
		profileToChampion.setRole(role);

		return profileToChampionRepository.save(profileToChampion);
	}

	public ProfileToChampion getRandomChampion(int id, Role role) {
		List<ProfileToChampion> champions = profileToChampionRepository.findRandomByProfileIdAndRole(id, role);
		return Random.collectionElement(champions);
	}

}
