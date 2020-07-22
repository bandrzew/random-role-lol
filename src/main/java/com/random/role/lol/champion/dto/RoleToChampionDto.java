package com.random.role.lol.champion.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.random.role.lol.champion.model.Role;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleToChampionDto implements Serializable {

	private static final long serialVersionUID = 6258499546783484151L;

	private ChampionDto champion;

	private Role role;

	public ChampionDto getChampion() {
		return champion;
	}

	public void setChampion(ChampionDto champion) {
		this.champion = champion;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
