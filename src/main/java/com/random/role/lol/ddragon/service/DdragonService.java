package com.random.role.lol.ddragon.service;

import com.random.role.lol.champion.model.Champion;
import com.random.role.lol.champion.repository.ChampionRepository;
import com.random.role.lol.ddragon.client.DdragonClient;
import com.random.role.lol.ddragon.resource.DdragonResource;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DdragonService {

	private final ChampionRepository championRepository;

	private final DdragonResource ddragonResource;

	@Autowired
	public DdragonService(ChampionRepository championRepository, DdragonClient ddragonClient) {
		this.championRepository = championRepository;
		this.ddragonResource = ddragonClient.getDdragonResource();
	}

	public void importChampions() {
		ddragonResource.getChampions(getMostRecentVersion()).getData().forEach((championName, importChampion) -> {
			Champion champion = championRepository.findByName(championName).orElseGet(Champion::new);
			champion.setName(championName);
			champion.setDisplayName(importChampion.getName());
			champion.setKey(Integer.parseInt(importChampion.getKey()));
			championRepository.save(champion);
		});
	}

	private String getMostRecentVersion() {
		return Optional.ofNullable(ddragonResource.getVersions())
				.map(List::iterator)
				.filter(Iterator::hasNext)
				.map(Iterator::next)
				.orElse(Strings.EMPTY);
	}

}
