package com.random.role.lol.profile.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.random.role.lol.profile.model.Profile;
import com.random.role.lol.profile.model.ProfileType;
import com.random.role.lol.profile.model.SpecialProfile;
import com.random.role.lol.profile.repository.ProfileRepository;
import com.random.role.lol.profile.repository.SpecialProfileRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProfileService {

	private final EntityManager em;

	private final ProfileRepository profileRepository;

	private final SpecialProfileRepository specialProfileRepository;

	public ProfileService(EntityManager em, ProfileRepository profileRepository, SpecialProfileRepository specialProfileRepository) {
		this.em = em;
		this.profileRepository = profileRepository;
		this.specialProfileRepository = specialProfileRepository;
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

}
