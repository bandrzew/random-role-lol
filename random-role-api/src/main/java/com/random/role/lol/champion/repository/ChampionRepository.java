package com.random.role.lol.champion.repository;

import com.random.role.lol.champion.model.Champion;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChampionRepository extends JpaRepository<Champion, Integer> {

	List<Champion> getAllByIdIn(Collection<Integer> ids);

}
