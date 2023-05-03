package com.random.role.lol.profile.controller;

import static java.util.Collections.singletonMap;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.random.role.lol.champion.dto.ChampionDto;
import com.random.role.lol.champion.dto.RoleToChampionDto;
import com.random.role.lol.champion.model.Role;
import com.random.role.lol.champion.serializer.ChampionSerializer;
import com.random.role.lol.common.randomizer.Random;
import com.random.role.lol.common.response.ResponseMapper;
import com.random.role.lol.profile.deserializer.ProfileDeserializer;
import com.random.role.lol.profile.dto.DrawResultDto;
import com.random.role.lol.profile.dto.ProfileToChampionDto;
import com.random.role.lol.profile.model.Profile;
import com.random.role.lol.profile.model.ProfileToChampion;
import com.random.role.lol.profile.serializer.ProfileSerializer;
import com.random.role.lol.profile.service.ProfileChampionService;
import com.random.role.lol.profile.service.ProfileService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/profiles/{id}/champions")
public class ProfileChampionController {

	private final ProfileService profileService;
	private final ProfileChampionService profileChampionService;

	public ProfileChampionController(ProfileService profileService, ProfileChampionService profileChampionService) {
		this.profileService = profileService;
		this.profileChampionService = profileChampionService;
	}

	@GetMapping
	public Map<Role, List<ChampionDto>> listChampions(@PathVariable("id") int id) {
		return profileService.get(id)
				.map(profileChampionService::listChampions)
				.orElseGet(Collections::emptyList)
				.stream()
				.map(ProfileSerializer::fromProfileToChampion)
				.collect(groupingBy(ProfileToChampionDto::getRole, mapping(ProfileToChampionDto::getChampion, toList())));
	}

	@PutMapping
	public ResponseEntity<ProfileToChampionDto> addChampion(@PathVariable int id,
			@RequestBody @Valid ProfileToChampionDto profileToChampionDto) {
		return profileChampionService.addChampion(id, ProfileDeserializer.fromDto(profileToChampionDto))
				.map(ProfileSerializer::fromProfileToChampion)
				.map(ResponseMapper::noContent)
				.orElseGet(ResponseMapper::notFound);
	}

	@DeleteMapping
	public ResponseEntity<Object> removeChampion(@PathVariable int id, @RequestBody @Valid ProfileToChampionDto profileToChampionDto) {
		return profileService.get(id).map(profile -> {
			if (profile.isRemovalRestricted())
				return HttpStatus.FORBIDDEN;

			ProfileToChampion profileToChampion = ProfileDeserializer.fromDto(profileToChampionDto);
			profileToChampion.setProfile(profile);
			profileChampionService.removeChampion(profileToChampion);

			return HttpStatus.NO_CONTENT;
		}).map(ResponseEntity::new).orElseGet(ResponseMapper::notFound);
	}

	@GetMapping("random")
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
				.map(role -> profileChampionService.getRandomChampion(id, role))
				.filter(Objects::nonNull)
				.map(profileToChampion -> ChampionSerializer.fromRoleAndChampion(profileToChampion.getRole(),
						profileToChampion.getChampion()))
				.collect(toList());
	}

}
