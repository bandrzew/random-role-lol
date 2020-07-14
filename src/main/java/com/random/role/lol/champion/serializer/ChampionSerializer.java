package com.random.role.lol.champion.serializer;

import com.random.role.lol.champion.dto.ChampionDto;
import com.random.role.lol.champion.model.Champion;

public class ChampionSerializer {

	public static ChampionDto toDto(Champion champion) {
		ChampionDto championDto = new ChampionDto();
		championDto.setId(champion.getId());
		championDto.setKey(champion.getKey());
		championDto.setName(champion.getName());

		return championDto;
	}

}
