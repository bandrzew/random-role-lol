package com.random.role.lol.champion.controller;

import static java.util.stream.Collectors.toMap;

import com.random.role.lol.champion.dto.ChampionDto;
import com.random.role.lol.champion.repository.ChampionRepository;
import com.random.role.lol.champion.serializer.ChampionSerializer;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/champions")
public class ChampionController {

	private final ChampionRepository championRepository;

	@Autowired
	public ChampionController(ChampionRepository championRepository) {
		this.championRepository = championRepository;
	}

	@GetMapping
	public Map<Integer, ChampionDto> champions() {
		return championRepository.findAll()
				.stream()
				.map(ChampionSerializer::toDto)
				.collect(toMap(ChampionDto::getKey, Function.identity()));
	}

}
