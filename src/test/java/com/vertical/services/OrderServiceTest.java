package com.vertical.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import java.io.File;
import java.io.FileInputStream;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import com.vertical.entities.Order;
import com.vertical.entities.User;
import com.vertical.filters.OrderFilter;
import com.vertical.repositories.OrderRepository;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

	private static final File FILE = new File( "src/test/resources/input_files/example.txt" );

	private static final Long FIRST_REPEATED_ID = 753L;
	private static final Long SECOND_REPEATED_ID = 798L;
	private static final Long FIRST_USER_ID = 70L;
	private static final Long SECOND_USER_ID = 75L;
	private static final String FIRST_USERNAME = "Palmer Prosacco";
	private static final String SECOND_USERNAME = "Bobbie Batz";

	@Mock
	private OrderRepository repository;
	@InjectMocks
	private OrderService service;

	@Captor
	private ArgumentCaptor<Specification<Order>> specificationCaptor;

	@Test
	void list() {
		service.list(new OrderFilter());

		then(repository).should().findAll(specificationCaptor.capture());
		assertThat(specificationCaptor.getValue()).isNotNull();
	}

	@Test
	void saveData() throws Exception {
		given(repository.findById(FIRST_REPEATED_ID)).willReturn(buildOrder(FIRST_USER_ID, FIRST_USERNAME ));
		given(repository.findById(SECOND_REPEATED_ID)).willReturn(buildOrder(SECOND_USER_ID, SECOND_USERNAME ));

		service.importDataFromFile(new FileInputStream(FILE));

		then(repository).should(times(12)).findById(anyLong());
		then(repository).should(times(10)).save(any(Order.class));
	}

	private Optional<Order> buildOrder(Long userId, String username) {
		return Optional.of(
				Order.builder()
				.user(new User(userId, username))
				.build()
		);
	}
}
