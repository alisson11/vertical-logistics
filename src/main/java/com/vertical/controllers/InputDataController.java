package com.vertical.controllers;

import java.io.IOException;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vertical.services.OrderService;

@RestController
@AllArgsConstructor
@RequestMapping(value = "input-orders")
public class InputDataController {

	private final OrderService service;

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void importData(@RequestParam("file") MultipartFile file) throws IOException {
		service.importDataFromFile(file.getInputStream());
	}
}
