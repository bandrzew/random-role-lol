package com.random.role.lol.profile.dto;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.random.role.lol.champion.dto.RoleToChampionDto;
import com.random.role.lol.champion.model.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DrawResultDto implements Serializable {

	@Serial
	private static final long serialVersionUID = -4808520527025183649L;

	private ProfileDto profile;

	private Map<Role, List<RoleToChampionDto>> firstRole;

	private Map<Role, List<RoleToChampionDto>> secondRole;

}
