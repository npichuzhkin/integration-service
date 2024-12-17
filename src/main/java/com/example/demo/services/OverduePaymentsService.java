package com.example.demo.services;

import com.example.demo.dto.OverduePaymentResponseDTO;
import com.example.demo.exceptions.OverduePaymentException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OverduePaymentsService {
    List<OverduePaymentResponseDTO> getOverduePayments() throws OverduePaymentException;
}
