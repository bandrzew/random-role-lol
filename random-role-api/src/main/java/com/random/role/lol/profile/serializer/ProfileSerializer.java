package com.random.role.lol.profile.serializer;

import com.random.role.lol.champion.serializer.ChampionSerializer;
import com.random.role.lol.profile.dto.ProfileDto;
import com.random.role.lol.profile.dto.ProfileToChampionDto;
import com.random.role.lol.profile.model.Profile;
import com.random.role.lol.profile.model.ProfileToChampion;
import com.random.role.lol.profile.model.SpecialProfile;

public class ProfileSerializer {

	public static ProfileDto fromProfile(Profile profile) {
		ProfileDto profileDto = new ProfileDto();
		profileDto.setId(profile.getId());
		profileDto.setName(profile.getName());
		if (profile instanceof SpecialProfile)
			profileDto.setDescription(((SpecialProfile) profile).getProfileType().getDescription());

		return profileDto;
	}

	public static ProfileToChampionDto fromProfileToChampion(ProfileToChampion profileToChampion) {
		ProfileToChampionDto profileToChampionDto = new ProfileToChampionDto();
		profileToChampionDto.setChampion(ChampionSerializer.base(profileToChampion.getChampion()));
		profileToChampionDto.setRole(profileToChampion.getRole());

		return profileToChampionDto;
	}

}
