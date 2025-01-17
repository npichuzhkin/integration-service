package com.example.demo;

import com.example.demo.controllers.OverduePaymentsController;
import com.example.demo.dto.OverduePaymentResponseDTO;
import com.example.demo.exceptions.OverduePaymentException;
import com.example.demo.services.OverduePaymentsServiceImpl;
import javacode.prac.api.client.feign.adapter.customer.dto.CustomerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import payment.OrderEntityDto;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class OverduePaymentsControllerTest {

	@Mock
	private OverduePaymentsServiceImpl overduePaymentsServiceImpl;

	@InjectMocks
	private OverduePaymentsController overduePaymentsController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void shouldReturnListOfOverduePayments() throws OverduePaymentException {
		CustomerDto customer1 = new CustomerDto();
		customer1.setUserName("John Doe");
		customer1.setInn("1234567890");

		OrderEntityDto order1 = new OrderEntityDto();
		order1.setOrderId("ORD-001");
		order1.setCustomerName("John Doe");

		CustomerDto customer2 = new CustomerDto();
		customer2.setUserName("Jane Smith");
		customer2.setInn("9876543210");

		OrderEntityDto order2 = new OrderEntityDto();
		order2.setOrderId("ORD-002");
		order2.setCustomerName("Jane Smith");

		List<OverduePaymentResponseDTO> mockPayments = Arrays.asList(
				new OverduePaymentResponseDTO(customer1, order1),
				new OverduePaymentResponseDTO(customer2, order2)
		);

		when(overduePaymentsServiceImpl.getOverduePayments()).thenReturn(mockPayments);

		ResponseEntity<List<OverduePaymentResponseDTO>> response = overduePaymentsController.showOverduePayments();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(mockPayments.size(), response.getBody().size());
		assertEquals(mockPayments, response.getBody());

		verify(overduePaymentsServiceImpl, times(1)).getOverduePayments();
	}

	@Test
	void shouldReturnEmptyListWhenNoOverduePayments() throws OverduePaymentException {
		when(overduePaymentsServiceImpl.getOverduePayments()).thenReturn(Collections.emptyList());

		ResponseEntity<List<OverduePaymentResponseDTO>> response = overduePaymentsController.showOverduePayments();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(0, response.getBody().size());

		verify(overduePaymentsServiceImpl, times(1)).getOverduePayments();
	}
}
