package com.random.role.lol.profile.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.random.role.lol.champion.dto.ChampionDto;
import com.random.role.lol.champion.model.Role;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileToChampionDto implements Serializable {

	private static final long serialVersionUID = -3429095307464479729L;

	private ProfileDto profile;

	private ChampionDto champion;

	private Role role;

	public ProfileDto getProfile() {
		return profile;
	}

	public void setProfile(ProfileDto profile) {
		this.profile = profile;
	}

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
