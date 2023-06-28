package com.vertical.mappers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.then;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.vertical.dtos.UserOrderDTO;
import com.vertical.entities.Order;
import com.vertical.entities.User;

@ExtendWith(MockitoExtension.class)
class UserOrderMapperTest {

	private static final Long FIRST_ORDER_ID = 1L;
	private static final Long SECOND_ORDER_ID = 2L;
	private static final Long THIRD_ORDER_ID = 3L;
	private static final Long FOURTH_ORDER_ID = 4L;
	private static final Long FIRST_USER_ID = 1L;
	private static final Long SECOND_USER_ID = 2L;
	private static final String FIRST_USERNAME = "Joao";
	private static final String SECOND_USERNAME = "Maria";
	private static final String USER_ID = "userId";
	private static final String USERNAME = "name";

	@Mock
	private OrderMapper orderMapper;
	@InjectMocks
	private UserOrderMapper mapper;

	@Test
	void map() {
		List<UserOrderDTO> userOrders = mapper.map(buildOrderList());

		then(orderMapper).should(Mockito.times(2)).map(anyList());
		assertThat(userOrders).hasSize(2);
		assertThat(userOrders.get(0)).extracting(USER_ID, USERNAME).containsExactly(FIRST_USER_ID, FIRST_USERNAME);
		assertThat(userOrders.get(1)).extracting(USER_ID, USERNAME).containsExactly(SECOND_USER_ID, SECOND_USERNAME);
	}

	private List<Order> buildOrderList() {
		return List.of(
				buildOrder(FIRST_ORDER_ID, FIRST_USER_ID, FIRST_USERNAME),
				buildOrder(SECOND_ORDER_ID, SECOND_USER_ID, SECOND_USERNAME),
				buildOrder(THIRD_ORDER_ID, FIRST_USER_ID, FIRST_USERNAME),
				buildOrder(FOURTH_ORDER_ID, SECOND_USER_ID, SECOND_USERNAME)
		);
	}

	private Order buildOrder(Long orderId, Long userId, String username) {
		return Order.builder()
				.id(orderId)
				.user(new User(userId, username))
				.build();
	}
}
