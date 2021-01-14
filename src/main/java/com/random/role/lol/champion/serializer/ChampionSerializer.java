package com.random.role.lol.champion.serializer;

import com.random.role.lol.champion.dto.ChampionDto;
import com.random.role.lol.champion.dto.RoleToChampionDto;
import com.random.role.lol.champion.model.Champion;
import com.random.role.lol.champion.model.Role;
import com.random.role.lol.profile.model.ProfileToChampion;
import java.util.Optional;

public class ChampionSerializer {

	public static ChampionDto base(Champion champion) {
		ChampionDto championDto = new ChampionDto();
		if (champion == null)
			return championDto;

		championDto.setId(champion.getId());
		championDto.setKey(champion.getKey());
		championDto.setName(Optional.ofNullable(champion.getDisplayName()).orElse(champion.getName()));

		return championDto;
	}

	public static RoleToChampionDto fromProfileToChampion(ProfileToChampion profileToChampion) {
		return fromRoleAndChampion(profileToChampion.getRole(), profileToChampion.getChampion());
	}

	public static RoleToChampionDto fromRoleAndChampion(Role role, Champion champion) {
		RoleToChampionDto roleToChampionDto = new RoleToChampionDto();
		roleToChampionDto.setRole(role);
		roleToChampionDto.setChampion(base(champion));

		return roleToChampionDto;
	}

}
