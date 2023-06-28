package com.vertical.mappers;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;

import com.vertical.dtos.ProductDTO;
import com.vertical.entities.OrderRegistration;
import com.vertical.entities.Product;

class OrderRegistrationMapperTest {

	private static final Long PRODUCT_ID = 11L;

	private OrderRegistrationMapper mapper = new OrderRegistrationMapper();

	@Test
	void map() {
		List<ProductDTO> resultList = mapper.map( buildOrderRegistrations() );

		assertThat(resultList).hasSize(1)
				.extracting("productId", "value")
				.containsExactly(Tuple.tuple(PRODUCT_ID, BigDecimal.TEN));
	}

	private List<OrderRegistration> buildOrderRegistrations() {
		return Collections.singletonList(
				OrderRegistration.builder()
				.value(BigDecimal.TEN)
				.product(new Product(PRODUCT_ID))
				.build()
		);
	}
}
