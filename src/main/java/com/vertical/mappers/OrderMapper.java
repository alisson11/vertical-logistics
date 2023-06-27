package com.vertical.mappers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Component;

import com.vertical.dtos.OrderDTO;
import com.vertical.dtos.ProductDTO;
import com.vertical.entities.Order;

@Component
@AllArgsConstructor
public class OrderMapper {

	private final OrderRegistrationMapper orderRegistrationMapper;

	public List<OrderDTO> map(List<Order> orders) {
		List<OrderDTO> result = new ArrayList<>();
		orders.forEach(order -> {
			OrderDTO orderDTO = toOrderDTO(order);
			postMapping(orderDTO);
			result.add(orderDTO);
		});
		return result;
	}

	private OrderDTO toOrderDTO(Order order) {
		return OrderDTO.builder()
				.orderId(order.getId())
				.date(order.getCreatedAt())
				.products(orderRegistrationMapper.map(order.getOrderRegistrations()))
				.build();
	}

	private void postMapping(OrderDTO orderDTO) {
		BigDecimal totalOrderValue = orderDTO.getProducts().stream()
				.map(ProductDTO::getValue)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		orderDTO.setTotal(totalOrderValue);
	}
}
