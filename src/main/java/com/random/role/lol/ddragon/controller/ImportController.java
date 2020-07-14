package com.random.role.lol.ddragon.controller;

import com.random.role.lol.ddragon.service.DdragonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/import")
public class ImportController {

	private final DdragonService ddragonService;

	@Autowired
	public ImportController(DdragonService ddragonService) {
		this.ddragonService = ddragonService;
	}

	@PostMapping
	public void importChampions() {
		ddragonService.importChampions();
	}

}
