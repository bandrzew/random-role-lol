package com.random.role.lol.common.randomizer;

import static java.util.Collections.singleton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class Random {

	public static <T> T collectionElement(Collection<T> elements) {
		return collectionElement(elements, (T) null);
	}

	public static <T> T collectionElement(Collection<T> elements, T excluded) {
		return collectionElement(elements, singleton(excluded));
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
