package com.random.role.lol.profile.service;

import com.random.role.lol.champion.model.Champion;
import com.random.role.lol.champion.model.Role;
import com.random.role.lol.profile.model.Profile;
import com.random.role.lol.profile.model.ProfileToChampion;
import com.random.role.lol.profile.repository.ProfileToChampionRepository;
import com.random.role.lol.randomizer.Random;
import java.util.List;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProfileService {

	private final EntityManager em;

	private final ProfileToChampionRepository profileToChampionRepository;

	@Autowired
	public ProfileService(EntityManager em, ProfileToChampionRepository profileToChampionRepository) {
		this.em = em;
		this.profileToChampionRepository = profileToChampionRepository;
	}

	public Profile create(String profileName) {
		Profile profile = new Profile(profileName);
		em.persist(profile);
		return profile;
	}

	public List<ProfileToChampion> listChampions(int id){
		return profileToChampionRepository.findAllByProfile(em.getReference(Profile.class, id));
	}

	public List<ProfileToChampion> listChampionsByRole(int id, Role role){
		return profileToChampionRepository.findAllByProfileAndRole(em.getReference(Profile.class, id), role);
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
