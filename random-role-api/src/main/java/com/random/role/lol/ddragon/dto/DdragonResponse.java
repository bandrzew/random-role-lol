package com.random.role.lol.ddragon.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DdragonResponse implements Serializable {

	@Serial
	private static final long serialVersionUID = 3365457776044819375L;

	private String version;

	private Map<String, DdragonChampionDto> data;

	public DdragonResponse() {
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Map<String, DdragonChampionDto> getData() {
		return data;
	}

	public void setData(Map<String, DdragonChampionDto> data) {
		this.data = data;
	}

}
