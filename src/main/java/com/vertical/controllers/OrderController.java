package com.vertical.controllers;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vertical.dtos.UserOrderDTO;
import com.vertical.mappers.UserOrderMapper;
import com.vertical.filters.OrderFilter;
import com.vertical.services.OrderService;

@RestController
@AllArgsConstructor
@RequestMapping(value = "orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {
	private final OrderService service;
	private final UserOrderMapper userOrderMapper;

	@GetMapping
	public List<UserOrderDTO> findUserOrders(OrderFilter filter) {
		return Optional.ofNullable(service.list(filter))
				.map(userOrderMapper::map)
				.orElse(Collections.emptyList());
	}
}
