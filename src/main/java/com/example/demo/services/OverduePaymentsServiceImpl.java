package com.example.demo.services;

import com.example.demo.dto.DictionaryRequestDTO;
import com.example.demo.exceptions.OverduePaymentException;
import com.example.demo.exceptions.UnknownPaymentTermException;
import com.example.demo.models.InformationForCheck;
import com.example.demo.dto.OverduePaymentResponseDTO;
import customer.CustomerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import payment.OrderEntityDto;
import payment.Payment;

import java.time.Duration;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

@Service
public class OverduePaymentsServiceImpl implements OverduePaymentsService {

    private final InformationForCheckService infoService;

    @Autowired
    public OverduePaymentsServiceImpl(InformationForCheckService infoService) {
        this.infoService = infoService;
    }

    public List<OverduePaymentResponseDTO> getOverduePayments() throws OverduePaymentException {
        InformationForCheck info = infoService.getInformationForVerification();
        List<OverduePaymentResponseDTO> overduePayments = new LinkedList<>();

        for (OrderEntityDto order: info.getOrders()) {
            for (Payment payment: order.getPayments()) {
                if (payment.getStatus().toString().equals("UNPAID") &&
                        payment.getPlaneDate()
                            .plus(getDaysDependingOnCustomerType(order.getCustomerType(), info.getDictionary()))
                            .isBefore(payment.getActualDate() == null ? Instant.now() : payment.getActualDate())){
                    overduePayments.add(new OverduePaymentResponseDTO(info.getUsers().get(order.getUserLogin()), order));
                    break;
                }
            }
        }

        return overduePayments;
    }

    private Duration getDaysDependingOnCustomerType(CustomerType customerType, DictionaryRequestDTO dictionary) throws UnknownPaymentTermException {
        Duration daysToPay;
        try{
            daysToPay = Duration.ofDays(Long.parseLong(dictionary
                    .getValues().get(customerType.toString())
                    .substring(0,2)));
        } catch (NumberFormatException e){
            throw new UnknownPaymentTermException();
        }
        return daysToPay;
    }
}
