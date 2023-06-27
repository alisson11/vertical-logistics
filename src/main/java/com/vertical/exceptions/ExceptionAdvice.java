package com.vertical.exceptions;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.AllArgsConstructor;

import org.postgresql.util.PSQLException;
import org.postgresql.util.ServerErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vertical.dtos.ExceptionErrorDTO;

@RestController
@ControllerAdvice
@AllArgsConstructor
public class ExceptionAdvice {

	private static final Pattern ORDER_UNIQUE_PATTERN = Pattern.compile("\\(order_id, product_id, value\\)=\\(([\\d]+), ([\\d]+), ([\\d.]+)\\)");
	private static final String DEFAULT_ERROR_MESSAGE = "Ocorreu um erro ao importar os dados";

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(PSQLException.class)
	public ExceptionErrorDTO handlePostgresException(PSQLException exception) {
		return ExceptionErrorDTO.builder()
				.date(LocalDateTime.now())
				.status(HttpStatus.BAD_REQUEST.value())
				.message(getExceptionMessage(exception))
				.build();
	}

	private String getExceptionMessage(PSQLException exception) {
		return Optional.ofNullable(exception.getServerErrorMessage())
				.map(ServerErrorMessage::getDetail)
				.map(this::getMessageByDetail)
				.orElse(DEFAULT_ERROR_MESSAGE);
	}

	private String getMessageByDetail(String constraintDescription) {
		Matcher matcher = ORDER_UNIQUE_PATTERN.matcher(constraintDescription);
		return matcher.find() ? formatMessage(matcher.group(1), matcher.group(2), matcher.group(3)) : null;
	}

	private String formatMessage(String orderId, String productId, String value) {
		return String.format("Já existe um pedido de ID %s com o produto ID %s e valor %s", orderId, productId, value);
	}
}
