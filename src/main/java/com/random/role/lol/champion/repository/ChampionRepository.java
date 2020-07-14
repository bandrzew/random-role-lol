package com.random.role.lol.champion.repository;

import com.random.role.lol.champion.model.Champion;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChampionRepository extends JpaRepository<Champion, Integer> {

	Optional<Champion> findByKey(int key);

	Optional<Champion> findByName(String name);

}
