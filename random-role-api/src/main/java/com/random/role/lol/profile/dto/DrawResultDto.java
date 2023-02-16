package com.random.role.lol.profile.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.random.role.lol.champion.dto.RoleToChampionDto;
import com.random.role.lol.champion.model.Role;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DrawResultDto implements Serializable {

	@Serial
	private static final long serialVersionUID = -4808520527025183649L;

	private ProfileDto profile;

	private Map<Role, List<RoleToChampionDto>> firstRole;

	private Map<Role, List<RoleToChampionDto>> secondRole;

	public ProfileDto getProfile() {
		return profile;
	}

	public void setProfile(ProfileDto profile) {
		this.profile = profile;
	}

	public Map<Role, List<RoleToChampionDto>> getFirstRole() {
		return firstRole;
	}

	public void setFirstRole(Map<Role, List<RoleToChampionDto>> firstRole) {
		this.firstRole = firstRole;
	}

	public Map<Role, List<RoleToChampionDto>> getSecondRole() {
		return secondRole;
	}

	public void setSecondRole(Map<Role, List<RoleToChampionDto>> secondRole) {
		this.secondRole = secondRole;
	}

}
