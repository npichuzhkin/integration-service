package com.example.demo.controllers;

import com.example.demo.dto.OverduePaymentResponseDTO;
import com.example.demo.exceptions.OverduePaymentException;
import com.example.demo.services.OverduePaymentsServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class OverduePaymentsController {

    private final OverduePaymentsServiceImpl overduePaymentsServiceImpl;

    public OverduePaymentsController(OverduePaymentsServiceImpl overduePaymentsServiceImpl) {
        this.overduePaymentsServiceImpl = overduePaymentsServiceImpl;
    }

    @GetMapping("/overduePayments")
    public ResponseEntity<List<OverduePaymentResponseDTO>> showOverduePayments() throws OverduePaymentException {
        List<OverduePaymentResponseDTO> overduePayments = overduePaymentsServiceImpl.getOverduePayments();
        return new ResponseEntity<>(overduePayments, HttpStatus.OK);
    }

}
