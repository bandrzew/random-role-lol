package com.random.role.lol.common.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseMapper {

	public static <T> ResponseEntity<T> notFound() {
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	public static <T> ResponseEntity<T> ok(T dto) {
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

}
