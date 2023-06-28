package com.vertical.config;

import java.util.Objects;

import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresContainer extends PostgreSQLContainer<PostgresContainer> {

	private static final String POSTGRES_VERSION = "postgres:11.5";

	public static PostgresContainer instance;

	private PostgresContainer() {
		super(POSTGRES_VERSION);
	}

	public static PostgresContainer getInstance() {
		if (Objects.isNull(instance)) {
			instance = new PostgresContainer();
			instance.withReuse(true);
		}
		return instance;
	}

	public void start() {
		super.start();
		System.setProperty("spring.datasource.url", instance.getJdbcUrl());
		System.setProperty("spring.datasource.username", instance.getUsername());
		System.setProperty("spring.datasource.password", instance.getPassword());
	}
}
