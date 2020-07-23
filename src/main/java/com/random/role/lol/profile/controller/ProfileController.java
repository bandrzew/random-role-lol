package com.random.role.lol.profile.controller;

import static java.util.Collections.singletonMap;
import static java.util.stream.Collectors.toList;

import com.random.role.lol.champion.dto.RoleToChampionDto;
import com.random.role.lol.champion.model.Role;
import com.random.role.lol.champion.serializer.ChampionSerializer;
import com.random.role.lol.common.randomizer.Random;
import com.random.role.lol.common.response.ResponseMapper;
import com.random.role.lol.profile.deserializer.ProfileDeserializer;
import com.random.role.lol.profile.dto.DrawResultDto;
import com.random.role.lol.profile.dto.ProfileDto;
import com.random.role.lol.profile.dto.ProfileToChampionDto;
import com.random.role.lol.profile.model.Profile;
import com.random.role.lol.profile.serializer.ProfileSerializer;
import com.random.role.lol.profile.service.ProfileService;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/profiles")
public class ProfileController {

	private final ProfileService profileService;

	@Autowired
	public ProfileController(ProfileService profileService) {
		this.profileService = profileService;
	}

	@GetMapping
	public List<ProfileDto> list() {
		return profileService.list().stream().map(ProfileSerializer::fromProfile).collect(toList());
	}

	@GetMapping("{id}")
	public ResponseEntity<ProfileDto> get(@PathVariable("id") int id) {
		return profileService.get(id).map(ProfileSerializer::fromProfile).map(ResponseMapper::ok).orElseGet(ResponseMapper::notFound);
	}

	@PutMapping
	public ProfileDto create(@RequestBody ProfileDto profileDto) {
		return ProfileSerializer.fromProfile(profileService.create(profileDto.getName()));
	}

	@PostMapping("{id}")
	public ResponseEntity<ProfileDto> edit(@PathVariable("id") int id, @RequestBody ProfileDto profileDto) {
		return profileService.edit(id, ProfileDeserializer.fromDto(profileDto))
				.map(ProfileSerializer::fromProfile)
				.map(ResponseMapper::ok)
				.orElseGet(ResponseMapper::notFound);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<ProfileDto> delete(@PathVariable("id") int id) {
		return new ResponseEntity<>(profileService.delete(id) ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND);
	}

	@GetMapping("{id}/champions")
	public List<ProfileToChampionDto> listChampions(@PathVariable("id") int id) {
		return profileService.listChampions(id).stream().map(ProfileSerializer::fromProfileToChampion).collect(toList());
	}

	@PutMapping("{id}/champions")
	public void addChampion(@PathVariable int id, @RequestBody ProfileToChampionDto profileToChampionDto) {
		profileService.addChampion(id, profileToChampionDto.getChampion().getId(), profileToChampionDto.getRole());
	}

	@GetMapping("{id}/champions/{role}")
	public List<ProfileToChampionDto> listChampionsByRole(@PathVariable int id, @PathVariable Role role) {
		return profileService.listChampionsByRole(id, role).stream().map(ProfileSerializer::fromProfileToChampion).collect(toList());
	}

	@GetMapping("{id}/random")
	public DrawResultDto getRandom(@PathVariable int id, @RequestParam("roles") Set<Role> roles) {
		DrawResultDto drawResultDto = new DrawResultDto();
		Optional<Profile> profile = profileService.get(id);
		if (profile.isEmpty())
			return drawResultDto;

		drawResultDto.setProfile(ProfileSerializer.fromProfile(profile.get()));

		Role firstRole = Random.collectionElement(roles);
		List<RoleToChampionDto> firstRoleChampions = getRoleToChampions(id, roles, firstRole, null);
		drawResultDto.setFirstRole(singletonMap(firstRole, firstRoleChampions));

		if (roles.size() > 1 && firstRole != Role.FILL) {
			Role secondRole = Random.collectionElement(roles, firstRole);
			List<RoleToChampionDto> secondRoleChampions = getRoleToChampions(id, roles, secondRole, firstRole);
			drawResultDto.setSecondRole(singletonMap(secondRole, secondRoleChampions));
		}

		return drawResultDto;
	}

	private List<RoleToChampionDto> getRoleToChampions(int id, Set<Role> roles, Role drawnRole, Role excludedRole) {
		return Role.getActualPositions(drawnRole)
				.stream()
				.filter(roles::contains)
				.filter(role -> role != excludedRole)
				.map(role -> profileService.getRandomChampion(id, role))
				.map(profileToChampion -> ChampionSerializer.fromRoleAndChampion(profileToChampion.getRole(),
						profileToChampion.getChampion()))
				.collect(toList());
	}

}
