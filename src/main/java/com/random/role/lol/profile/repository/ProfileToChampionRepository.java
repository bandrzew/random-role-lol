package com.random.role.lol.profile.repository;

import com.random.role.lol.champion.model.Role;
import com.random.role.lol.profile.model.Profile;
import com.random.role.lol.profile.model.ProfileToChampion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProfileToChampionRepository extends JpaRepository<ProfileToChampion, Integer> {

	List<ProfileToChampion> findAllByProfile(Profile profile);

	List<ProfileToChampion> findAllByProfileAndRole(Profile profile, Role role);

	@Query("SELECT p FROM ProfileToChampion p WHERE p.profile.id = ?1 AND p.role = ?2")
	List<ProfileToChampion> findRandomByProfileIdAndRole(int profileId, Role role);

}
