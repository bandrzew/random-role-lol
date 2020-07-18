package com.random.role.lol.profile.repository;

import com.random.role.lol.profile.model.SpecialProfile;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecialProfileRepository extends JpaRepository<SpecialProfile, Integer> {

	Optional<SpecialProfile> findByProfileType(SpecialProfile.ProfileType profileType);

}
