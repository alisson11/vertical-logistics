package com.vertical.repositories.specifications;

import java.time.LocalDate;
import java.util.Objects;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import org.springframework.data.jpa.domain.Specification;

import com.vertical.entities.Order;
import com.vertical.filters.OrderFilter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OrderSpecification {

	public static Specification<Order> buildSpecification(OrderFilter filter) {
		var specification = Specification.where(buildWhere());
		if (Objects.nonNull(filter.getOrderId()))
			specification = specification.and(buildOrderId(filter.getOrderId()));
		if (Objects.nonNull(filter.getStartDate()) && Objects.nonNull(filter.getEndDate()))
			specification = specification.and(buildInterval(filter.getStartDate(), filter.getEndDate()));
		return specification;
	}

	private static Specification<Order> buildWhere() {
		return (root, criteriaQuery, criteriaBuilder) ->
				criteriaBuilder.isTrue(criteriaBuilder.literal(true));
	}

	private static Specification<Order> buildOrderId(Long id) {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
	}

	private static Specification<Order> buildInterval(LocalDate start, LocalDate end) {
		return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.between(root.get("createdAt"), start, end);
	}
}
