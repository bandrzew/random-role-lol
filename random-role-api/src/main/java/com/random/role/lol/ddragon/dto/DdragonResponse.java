package com.random.role.lol.ddragon.dto;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DdragonResponse implements Serializable {

	@Serial
	private static final long serialVersionUID = 3365457776044819375L;

	private String version;

	private Map<String, DdragonChampionDto> data;

}
