package com.random.role.lol.controller;

import static java.util.stream.Collectors.toList;

import com.random.role.lol.dto.ChampionDto;
import com.random.role.lol.model.Champion;
import com.random.role.lol.service.ChampionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/import")
public class ImportController {

	private final ChampionService championService;

	@Autowired
	public ImportController(ChampionService championService) {
		this.championService = championService;
	}

	@PostMapping
	public void importChampions() {
		championService.importChampions();
	}

}
