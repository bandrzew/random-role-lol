package com.random.role.lol.profile.dto;

import java.io.Serial;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.random.role.lol.champion.dto.ChampionDto;
import com.random.role.lol.champion.model.Role;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileToChampionDto implements Serializable {

	@Serial
	private static final long serialVersionUID = -3429095307464479729L;

	private ProfileDto profile;

	@NotNull
	private ChampionDto champion;

	@NotNull
	private Role role;

}
