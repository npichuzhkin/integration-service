package com.example.demo.dto;

import javacode.prac.api.client.feign.adapter.customer.dto.CustomerDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import payment.OrderEntityDto;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OverduePaymentResponseDTO {
    private CustomerDto user;
    private OrderEntityDto order;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OverduePaymentResponseDTO that = (OverduePaymentResponseDTO) o;
        return Objects.equals(user, that.user) && Objects.equals(order, that.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, order);
    }
}
