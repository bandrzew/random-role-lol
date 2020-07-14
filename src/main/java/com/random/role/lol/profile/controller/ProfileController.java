package com.random.role.lol.profile.controller;

import static java.util.stream.Collectors.toList;

import com.random.role.lol.champion.model.Role;
import com.random.role.lol.profile.dto.ProfileDto;
import com.random.role.lol.profile.dto.ProfileToChampionDto;
import com.random.role.lol.profile.serializer.ProfileSerializer;
import com.random.role.lol.profile.service.ProfileService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/profiles")
public class ProfileController {

	private final ProfileService profileService;

	@Autowired
	public ProfileController(ProfileService profileService) {
		this.profileService = profileService;
	}

	@PutMapping
	public ProfileDto create(@RequestBody ProfileDto profileDto) {
		return ProfileSerializer.fromProfile(profileService.create(profileDto.getName()));
	}

	@PutMapping("{id}/champions")
	public void addChampion(@PathVariable int id, @RequestBody ProfileToChampionDto profileToChampionDto) {
		profileService.addChampion(id, profileToChampionDto.getChampion().getId(), profileToChampionDto.getRole());
	}

	@GetMapping("{id}/champions")
	public List<ProfileToChampionDto> listChampions(@PathVariable int id) {
		return profileService.listChampions(id).stream().map(ProfileSerializer::fromProfileToChampion).collect(toList());
	}

	@GetMapping("{id}/champions/{role}")
	public List<ProfileToChampionDto> listChampionsByRole(@PathVariable int id, @PathVariable Role role) {
		return profileService.listChampionsByRole(id, role).stream().map(ProfileSerializer::fromProfileToChampion).collect(toList());
	}

	@GetMapping("{id}/random/{role}")
	public ProfileToChampionDto getRandom(@PathVariable int id, @PathVariable Role role) {
		return ProfileSerializer.fromProfileToChampion(profileService.getRandomChampion(id, role));
	}

}
