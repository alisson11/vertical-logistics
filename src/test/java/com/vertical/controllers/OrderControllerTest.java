package com.vertical.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.ResourceUtils;
import org.testcontainers.shaded.org.apache.commons.io.FileUtils;

import com.vertical.config.AbstractDatabaseContainer;

@Sql(scripts = "classpath:sql_scripts/create_orders.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class OrderControllerTest extends AbstractDatabaseContainer {

	private static final String ORDER_PATH = "/orders";
	private static final String FILTER_BY_ORDER_ID_RESULT_JSON = "filter-by-order-id-result.json";
	private static final String FILTER_BY_PERIOD_RESULT_JSON = "filter-by-period-result.json";

	@Autowired
	private MockMvc mockMvc;

	@Test
	void findOrdersByOrderId() throws Exception {
		mockMvc
				.perform(get(ORDER_PATH).queryParam("orderId", "1"))
				.andExpect(status().isOk())
				.andExpect(content().json(getContentAsString(FILTER_BY_ORDER_ID_RESULT_JSON)));
	}

	@Test
	void findOrdersByPeriod() throws Exception {
		mockMvc
				.perform(get(ORDER_PATH)
						.queryParam("startDate", "2021-09-01")
						.queryParam("endDate", "2021-10-31"))
				.andExpect(status().isOk())
				.andExpect(content().json(getContentAsString(FILTER_BY_PERIOD_RESULT_JSON)));
	}

	private String getContentAsString(String filePath) throws Exception {
		return FileUtils.readFileToString(ResourceUtils.getFile("classpath:result_json/".concat(filePath)),
				Charset.defaultCharset());
	}
}
