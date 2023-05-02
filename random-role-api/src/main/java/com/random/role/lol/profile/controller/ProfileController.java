package com.random.role.lol.profile.controller;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.random.role.lol.common.response.ResponseMapper;
import com.random.role.lol.profile.deserializer.ProfileDeserializer;
import com.random.role.lol.profile.dto.ProfileDto;
import com.random.role.lol.profile.serializer.ProfileSerializer;
import com.random.role.lol.profile.service.ProfileService;

@RestController
@RequestMapping(path = "/profiles")
public class ProfileController {

	private final ProfileService profileService;

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
		return profileService.get(id).flatMap(profile -> {
			if (profile.isRemovalRestricted())
				return Optional.of(new ResponseEntity<ProfileDto>(HttpStatus.FORBIDDEN));

			return profileService.edit(profile.getId(), ProfileDeserializer.toProfile(profileDto))
					.map(ProfileSerializer::fromProfile)
					.map(ResponseMapper::ok);
		}).orElseGet(ResponseMapper::notFound);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Object> delete(@PathVariable("id") int id) {
		return profileService.get(id).map(profile -> {
			if (profile.isRemovalRestricted())
				return HttpStatus.FORBIDDEN;

			profileService.delete(profile);
			return HttpStatus.NO_CONTENT;
		}).map(ResponseEntity::new).orElseGet(ResponseMapper::notFound);
	}

}
