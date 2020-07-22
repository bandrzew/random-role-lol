package com.random.role.lol.champion.serializer;

import com.random.role.lol.champion.dto.ChampionDto;
import com.random.role.lol.champion.dto.RoleToChampionDto;
import com.random.role.lol.champion.model.Champion;
import com.random.role.lol.champion.model.Role;
import com.random.role.lol.profile.model.ProfileToChampion;

public class ChampionSerializer {

	public static ChampionDto toDto(Champion champion) {
		ChampionDto championDto = new ChampionDto();
		championDto.setId(champion.getId());
		championDto.setKey(champion.getKey());
		championDto.setName(champion.getName());

		return championDto;
	}

	public static RoleToChampionDto fromProfileToChampion(ProfileToChampion profileToChampion) {
		return fromRoleAndChampion(profileToChampion.getRole(), profileToChampion.getChampion());
	}

	public static RoleToChampionDto fromRoleAndChampion(Role role, Champion champion) {
		RoleToChampionDto roleToChampionDto = new RoleToChampionDto();
		roleToChampionDto.setRole(role);
		roleToChampionDto.setChampion(toDto(champion));

		return roleToChampionDto;
	}

}
