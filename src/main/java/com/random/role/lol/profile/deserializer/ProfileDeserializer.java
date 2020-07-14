package com.random.role.lol.profile.deserializer;

import com.random.role.lol.profile.dto.ProfileDto;
import com.random.role.lol.profile.model.Profile;
import java.util.Optional;

public class ProfileDeserializer {

	public static Profile fromDto(ProfileDto profileDto) {
		Profile profile = new Profile();
		Optional.ofNullable(profileDto.getId()).ifPresent(profile::setId);
		profile.setName(profileDto.getName());

		return profile;
	}

}
