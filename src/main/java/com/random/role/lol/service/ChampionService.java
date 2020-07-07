package com.random.role.lol.service;

import com.random.role.lol.client.DdragonClient;
import com.random.role.lol.model.Champion;
import com.random.role.lol.repository.ChampionRepository;
import com.random.role.lol.resource.DdragonResource;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChampionService {

	private final ChampionRepository championRepository;

	private final DdragonResource ddragonResource;

	@Autowired
	public ChampionService(ChampionRepository championRepository, DdragonClient ddragonClient) {
		this.championRepository = championRepository;
		this.ddragonResource = ddragonClient.getDdragonResource();
	}

	public List<Champion> list() {
		return championRepository.findAll();
	}

	public void importChampions() {
		ddragonResource.getChampions(getMostRecentVersion())
				.getData()
				.keySet()
				.forEach(championName -> championRepository.findByName(championName)
						.orElseGet(() -> championRepository.save(new Champion(championName))));
	}

	private String getMostRecentVersion() {
		return Optional.ofNullable(ddragonResource.getVersions())
				.map(List::iterator)
				.filter(Iterator::hasNext)
				.map(Iterator::next)
				.orElse(Strings.EMPTY);
	}

}
