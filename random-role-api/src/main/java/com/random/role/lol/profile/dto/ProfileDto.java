package com.random.role.lol.profile.dto;

import java.io.Serial;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDto implements Serializable {

	@Serial
	private static final long serialVersionUID = 6477680197882114677L;

	private Integer id;

	private String name;

	private String description;

}
