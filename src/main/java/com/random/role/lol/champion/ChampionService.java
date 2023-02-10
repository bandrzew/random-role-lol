package com.random.role.lol.champion;

import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

import com.random.role.lol.champion.model.Champion;
import com.random.role.lol.champion.repository.ChampionRepository;
import com.random.role.lol.profile.model.ProfileToChampion;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ChampionService {

	private final EntityManager em;

	private final ChampionRepository championRepository;

	public ChampionService(EntityManager em, ChampionRepository championRepository) {
		this.em = em;
		this.championRepository = championRepository;
	}

	public void fetchChampions(Collection<ProfileToChampion> profileToChampions) {
		if (profileToChampions == null || profileToChampions.isEmpty())
			return;

		Set<Integer> championIds = profileToChampions.stream().map(ProfileToChampion::getChampion).map(Champion::getId).collect(toSet());
		Map<Integer, Champion> idToChampion = championRepository.getAllByIdIn(championIds)
				.stream()
				.collect(toMap(Champion::getId, Function.identity()));

		for (ProfileToChampion profileToChampion : profileToChampions) {
			em.detach(profileToChampion);
			profileToChampion.setChampion(idToChampion.get(profileToChampion.getChampion().getId()));
		}
	}

}
