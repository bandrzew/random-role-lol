package com.random.role.lol.model;

import java.util.Set;

public enum Role {
	TOP, JUNGLE, MID, BOTTOM, SUPPORT, FILL;

	public static final Set<Role> ALL = Set.of(values());

	public static final Set<Role> POSITIONS = Set.of(TOP, JUNGLE, MID, BOTTOM, SUPPORT);

}
