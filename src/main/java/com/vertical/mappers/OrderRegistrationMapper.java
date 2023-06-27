package com.vertical.mappers;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Component;

import com.vertical.dtos.ProductDTO;
import com.vertical.entities.OrderRegistration;

@Component
@AllArgsConstructor
public class OrderRegistrationMapper {

	public List<ProductDTO> map(List<OrderRegistration> orderRegistrations) {
		return orderRegistrations.stream().map(this::toProductDTO).collect(Collectors.toList());
	}

	private ProductDTO toProductDTO(OrderRegistration orderRegistration) {
		return ProductDTO.builder()
				.productId(orderRegistration.getProduct().getId())
				.value(orderRegistration.getValue())
				.build();
	}
}
