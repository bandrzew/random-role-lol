package com.random.role.lol.champion.model;

import static java.util.Collections.emptySet;
import static java.util.Collections.singleton;

import java.util.Set;

public enum Role {
	TOP, JUNGLE, MID, BOTTOM, SUPPORT, FILL;

	public static final Set<Role> ALL = Set.of(values());

	public static final Set<Role> POSITIONS = Set.of(TOP, JUNGLE, MID, BOTTOM, SUPPORT);

	public static Set<Role> getActualPositions(Role role) {
		if (role == null)
			return emptySet();

		if (FILL.equals(role))
			return POSITIONS;

		return singleton(role);
	}

}
