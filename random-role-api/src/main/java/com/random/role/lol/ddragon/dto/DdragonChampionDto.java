package com.random.role.lol.ddragon.dto;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DdragonChampionDto implements Serializable {

	@Serial
	private static final long serialVersionUID = -4831989891940652018L;

	private String version;

	private String id;

	private String key;

	private String name;

	private String title;

	private List<String> tags;

}
