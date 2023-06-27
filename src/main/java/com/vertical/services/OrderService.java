package com.vertical.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import com.vertical.entities.Order;
import com.vertical.entities.OrderRegistration;
import com.vertical.entities.User;
import com.vertical.entities.Product;
import com.vertical.filters.OrderFilter;
import com.vertical.repositories.OrderRepository;
import com.vertical.repositories.specifications.OrderSpecification;

@Service
@AllArgsConstructor
public class OrderService {

	private final OrderRepository repository;

	public List<Order> list(OrderFilter filter) {
		var specification = OrderSpecification.buildSpecification(filter);
		return repository.findAll(specification);
	}
	
	public void importDataFromFile(InputStream inputStream) throws IOException {
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
			while (reader.ready())
				extractLineAndInsertOnDatabase(reader.readLine());
		}
	}

	private void extractLineAndInsertOnDatabase(String line) {
		String userId = line.substring(0, 10);
		String username = line.substring(10, 55).trim();
		String orderId = line.substring(55, 65);
		String productId = line.substring(65, 75);
		String value = line.substring(75, 87).trim();
		String date = line.substring(87, 95);
		LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE);
		Long orderIdAsLong = Long.valueOf(orderId);
		Long userIdAsLong = Long.valueOf(userId);
		if (!orderExistByDifferentUser(orderIdAsLong, userIdAsLong, username)) {
			User user = new User(userIdAsLong, username);
			Product product = new Product(Long.valueOf(productId));
			OrderRegistration orderRegistration = new OrderRegistration(product, new BigDecimal(value));
			Order order = buildOrder(orderIdAsLong, user, localDate, orderRegistration);
			setRelationships(order, orderRegistration);
			repository.save(order);
		}
	}

	private boolean orderExistByDifferentUser(Long orderId, Long userId, String username) {
		return repository.findById(orderId)
				.map(Order::getUser)
				.map(user -> !(user.getId().equals(userId) && user.getName().equalsIgnoreCase(username)))
				.orElse(false);
	}

	private void setRelationships(Order order, OrderRegistration orderRegistration) {
		orderRegistration.setOrder(order);
	}

	private Order buildOrder(Long orderId, User user, LocalDate date, OrderRegistration orderRegistration) {
		return Order.builder()
				.id(orderId)
				.user(user)
				.orderRegistrations(Collections.singletonList(orderRegistration))
				.createdAt(date)
				.build();
	}
}
