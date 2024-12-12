package com.example.demo.controllers;

import com.example.demo.dto.OverduePaymentDTO;
import com.example.demo.services.OverduePaymentsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class OverduePaymentsController {

    private final OverduePaymentsService overduePaymentsService;

    public OverduePaymentsController(OverduePaymentsService overduePaymentsService) {
        this.overduePaymentsService = overduePaymentsService;
    }

    @GetMapping("/overduePayments")
    public ResponseEntity<List<OverduePaymentDTO>> showOverduePayments(){
        List<OverduePaymentDTO> overduePayments = overduePaymentsService.getOverduePayments();
        return new ResponseEntity<>(overduePayments, HttpStatus.OK);
    }

}
