package com.vertical.mappers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.vertical.dtos.OrderDTO;
import com.vertical.dtos.ProductDTO;
import com.vertical.entities.Order;
import com.vertical.entities.OrderRegistration;

@ExtendWith(MockitoExtension.class)
class OrderMapperTest {

	private static final Long ORDER_ID = 11L;
	private static final BigDecimal TOTAL = BigDecimal.valueOf(11L);
	private static final LocalDate DATE = LocalDate.now();
	@Mock
	private OrderRegistrationMapper orderRegistrationMapper;
	@InjectMocks
	private OrderMapper mapper;

	@Test
	void map() {
		List<OrderRegistration> orderRegistrations = buildOrderRegistrationList();
		List<ProductDTO> productDTOList = buildProductDTOList();
		given(orderRegistrationMapper.map(orderRegistrations)).willReturn(productDTOList);

		List<OrderDTO> orderDTOList = mapper.map(buildOrderList(orderRegistrations));

		then(orderRegistrationMapper).should().map(orderRegistrations);
		assertThat(orderDTOList).hasSize(1)
				.extracting("orderId", "total", "date")
				.containsExactly(Tuple.tuple(ORDER_ID, TOTAL, DATE));
		assertThat(orderDTOList.get(0).getProducts()).hasSize(2).containsExactlyElementsOf(productDTOList);
	}

	private List<Order> buildOrderList(List<OrderRegistration> orderRegistrations) {
		return List.of(
				Order.builder()
						.id(ORDER_ID)
						.orderRegistrations(orderRegistrations)
						.createdAt(DATE)
						.build()
		);
	}

	private List<OrderRegistration> buildOrderRegistrationList() {
		return List.of(new OrderRegistration());
	}

	private List<ProductDTO> buildProductDTOList() {
		return List.of(
				new ProductDTO(1L, BigDecimal.ONE),
				new ProductDTO(2L, BigDecimal.TEN)
		);
	}
}
