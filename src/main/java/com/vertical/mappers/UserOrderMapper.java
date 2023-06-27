package com.vertical.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Component;

import com.vertical.dtos.UserOrderDTO;
import com.vertical.entities.Order;
import com.vertical.entities.User;

@Component
@AllArgsConstructor
public class UserOrderMapper {

	private final OrderMapper orderMapper;

	public List<UserOrderDTO> map(List<Order> orders) {
		List<UserOrderDTO> result = new ArrayList<>();
		Map<User, List<Order>> userOrders = orders.stream().collect(Collectors.groupingBy(Order::getUser));
		userOrders.forEach((user, orderList) -> result.add(toUserOrderDTO(user, orderList)));
		return result;
	}

	private UserOrderDTO toUserOrderDTO(User user, List<Order> orders) {
		return UserOrderDTO.builder()
				.userId(user.getId())
				.name(user.getName())
				.orders(orderMapper.map(orders))
				.build();
	}
}
