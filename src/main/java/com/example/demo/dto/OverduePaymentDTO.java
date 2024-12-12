package com.example.demo.dto;

import customer.User;
import javacode.prac.api.client.feign.adapter.customer.dto.CustomerDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import payment.OrderEntityDto;
import payment.Payment;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OverduePaymentDTO {
    private CustomerDto user;
    private OrderEntityDto order;
}
