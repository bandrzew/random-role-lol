package com.random.role.lol;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;

@DataJpaTest
class LolApplicationTests {

	private final DataSource dataSource;

	private final JdbcTemplate jdbcTemplate;

	private final EntityManager entityManager;

	@Autowired
	public LolApplicationTests(DataSource dataSource, JdbcTemplate jdbcTemplate, EntityManager entityManager) {
		this.dataSource = dataSource;
		this.jdbcTemplate = jdbcTemplate;
		this.entityManager = entityManager;
	}

	@Test
	public void contextLoads() {
	}

	@Test
	void injectedComponentsAreNotNull() {
		assertNotNull(dataSource);
		assertNotNull(jdbcTemplate);
		assertNotNull(entityManager);
	}

}
