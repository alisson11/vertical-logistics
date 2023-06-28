package com.vertical.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileInputStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import com.vertical.config.AbstractDatabaseContainer;

@Sql(scripts = "classpath:sql_scripts/delete_orders.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class InputDataControllerTest extends AbstractDatabaseContainer {
	private static final File FILE = new File( "src/test/resources/input_files/example.txt" );
	private static final String FILENAME = "file";
	private static final String INPUT_PATH = "/input-orders";
	private FileInputStream fileInputStream;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	public void setupFile() throws Exception {
		fileInputStream = new FileInputStream(FILE);
	}

	@AfterEach
	public void closeFile() throws Exception {
		fileInputStream.close();
	}

	@Test
	void inputDataWhenSuccess() throws Exception {
		byte[] fileBytes = fileInputStream.readAllBytes();

		mockMvc
				.perform(multipart(INPUT_PATH).file(FILENAME, fileBytes))
				.andExpect(status().isCreated());
	}

	@Test
	void inputDuplicatedData() throws Exception {
		byte[] fileBytes = fileInputStream.readAllBytes();

		mockMvc
				.perform(multipart(INPUT_PATH).file(FILENAME, fileBytes))
				.andExpect(status().isCreated());
		mockMvc
				.perform(multipart(INPUT_PATH).file(FILENAME, fileBytes))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.date").exists())
				.andExpect(jsonPath("$.status").value("400"))
				.andExpect(jsonPath("$.message").value( "Já existe um pedido de ID 753 com o produto ID 3 e valor 1836.74"));
	}
}
