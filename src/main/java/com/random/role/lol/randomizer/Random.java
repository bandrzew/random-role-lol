package com.random.role.lol.randomizer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class Random {

	public static <T> T collectionElement(Collection<T> elements) {
		return collectionElement(elements, null);
	}

	public static <T> T collectionElement(Collection<T> elements, Collection<T> excluded) {
		List<T> list = new ArrayList<>(elements);
		Optional.ofNullable(excluded).ifPresent(list::removeAll);
		return list.get(integer(list.size()));
	}

	private static int integer(int bound) {
		return ThreadLocalRandom.current().nextInt(0, bound);
	}

}
