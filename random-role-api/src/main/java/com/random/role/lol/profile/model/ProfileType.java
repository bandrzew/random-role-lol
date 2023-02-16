package com.random.role.lol.profile.model;

public enum ProfileType {

	//@formatter:off
	ALL_CHAMPIONS("Hardcore", "All champions in all roles - fun and LP loss guaranteed"),
	FREE_ROTATION("", "");
	//@formatter:on

	private final String name;

	private final String description;

	ProfileType(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
}
