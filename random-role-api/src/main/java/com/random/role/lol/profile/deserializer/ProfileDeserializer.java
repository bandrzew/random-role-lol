package com.random.role.lol.profile.deserializer;

import com.random.role.lol.champion.deserializer.ChampionDeserializer;
import com.random.role.lol.profile.dto.ProfileDto;
import com.random.role.lol.profile.dto.ProfileToChampionDto;
import com.random.role.lol.profile.model.Profile;
import com.random.role.lol.profile.model.ProfileToChampion;
import java.util.Optional;

public class ProfileDeserializer {

	public static Profile toProfile(ProfileDto profileDto) {
		Profile profile = new Profile();
		if (profileDto == null)
			return profile;

		Optional.ofNullable(profileDto.getId()).ifPresent(profile::setId);
		profile.setName(profileDto.getName());

		return profile;
	}

	public static ProfileToChampion fromDto(ProfileToChampionDto profileToChampionDto) {
		ProfileToChampion profileToChampion = new ProfileToChampion();
		profileToChampion.setProfile(toProfile(profileToChampionDto.getProfile()));
		profileToChampion.setChampion(ChampionDeserializer.toChampion(profileToChampionDto.getChampion()));
		profileToChampion.setRole(profileToChampionDto.getRole());

		return profileToChampion;
	}

}
