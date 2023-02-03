package com.random.role.lol.champion.controller;

import static java.util.stream.Collectors.toList;

import com.random.role.lol.champion.dto.ChampionDto;
import com.random.role.lol.champion.repository.ChampionRepository;
import com.random.role.lol.champion.serializer.ChampionSerializer;
import com.random.role.lol.common.response.ResponseMapper;
import com.random.role.lol.ddragon.service.DdragonService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/champions")
public class ChampionController {

	private final ChampionRepository championRepository;

	private final DdragonService ddragonService;

	@Autowired
	public ChampionController(ChampionRepository championRepository, DdragonService ddragonService) {
		this.championRepository = championRepository;
		this.ddragonService = ddragonService;
	}

	@GetMapping
	public List<ChampionDto> champions() {
		return championRepository.findAll().stream().map(ChampionSerializer::base).collect(toList());
	}

	@GetMapping("{id}")
	public ResponseEntity<ChampionDto> get(@PathVariable("id") int id) {
		return championRepository.findById(id).map(ChampionSerializer::base).map(ResponseMapper::ok).orElseGet(ResponseMapper::notFound);
	}

	@PostMapping("/import")
	public void importChampions() {
		ddragonService.importChampions();
	}

}
