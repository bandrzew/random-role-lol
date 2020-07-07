package com.random.role.lol.repository;

import com.random.role.lol.model.Champion;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChampionRepository extends JpaRepository<Champion, Integer> {

	Optional<Champion> findByName(String name);

}
