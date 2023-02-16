package com.random.role.lol.profile.repository;

import com.random.role.lol.profile.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {

}
