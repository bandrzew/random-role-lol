package com.random.role.lol.champion.deserializer;

import com.random.role.lol.champion.dto.ChampionDto;
import com.random.role.lol.champion.model.Champion;
import java.util.Optional;

public class ChampionDeserializer {

	public static Champion toChampion(ChampionDto championDto) {
		Champion champion = new Champion();
		if (championDto == null)
			return champion;

		Optional.ofNullable(championDto.getId()).ifPresent(champion::setId);
		Optional.ofNullable(championDto.getKey()).ifPresent(champion::setKey);

		return champion;
	}

}
