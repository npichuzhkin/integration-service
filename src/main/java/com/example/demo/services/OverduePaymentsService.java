package com.example.demo.services;

import com.example.demo.remoteClients.ContentAdminClient;
import com.example.demo.dto.DictionaryDTO;
import com.example.demo.dto.OverduePaymentDTO;
import customer.CustomerType;
import javacode.prac.api.client.feign.adapter.customer.dto.CustomerDto;
import org.springframework.data.domain.Page;
import javacode.prac.api.client.feign.adapter.customer.CustomerServiceClient;
import javacode.prac.api.client.feign.adapter.onec.OneCAdapterClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import payment.OrderEntityDto;
import payment.Payment;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class OverduePaymentsService {

    private final ContentAdminClient contentAdminClient;
    private final CustomerServiceClient customerServiceClient;
    private final OneCAdapterClient oneCAdapterClient;

    private static DictionaryDTO directoryInfo;
    private static Map<String, CustomerDto> users;
    private static List<OrderEntityDto> orderListForEachUser;

    private static LocalDateTime timeForRefreshInfo;

    @Autowired
    public OverduePaymentsService(ContentAdminClient contentAdminClient, CustomerServiceClient customerServiceClient, OneCAdapterClient oneCAdapterClient) {
        this.contentAdminClient = contentAdminClient;
        this.customerServiceClient = customerServiceClient;
        this.oneCAdapterClient = oneCAdapterClient;
        timeForRefreshInfo = LocalDateTime.now().minus(Duration.ofHours(1));
    }

    public List<OverduePaymentDTO> getOverduePayments(){
        checkTimeAndRefreshInformation();
        List<OverduePaymentDTO> overduePayments = new LinkedList<>();

        for (OrderEntityDto order: orderListForEachUser) {
            for (Payment payment: order.getPayments()) {
                if (payment.getStatus().toString().equals("UNPAID") &&
                        payment.getPlaneDate()
                            .plus(getDaysDependingOnCustomerType(order.getCustomerType()))
                            .isBefore(payment.getActualDate() == null ? Instant.now() : payment.getActualDate())){
                    overduePayments.add(new OverduePaymentDTO(users.get(order.getUserLogin()), order));
                    break;
                }
            }
        }

        return overduePayments;
    }

    private void checkTimeAndRefreshInformation(){
        if (timeForRefreshInfo.isBefore(LocalDateTime.now().minus(Duration.ofHours(1)))){
            timeForRefreshInfo = LocalDateTime.now();
            getInformationForVerification();
        }
    }

    private void getInformationForVerification(){
        directoryInfo = contentAdminClient.getDictionaryByName("PAYMENT_DEADLINE").getBody();
        getUsers();
        getOrders();
    }

    private void getUsers(){
        users = new HashMap<>();
        int userPageSize = 50;

        Page<CustomerDto> userPage = customerServiceClient.getCustomers(PageRequest.of(0,userPageSize)).getBody();
        userPage.getContent().forEach(u -> users.put(u.getUserLogin(),u));

        while (userPage.hasNext()) {
            userPage = customerServiceClient.getCustomers(PageRequest.of((userPage.getNumber()+1),userPageSize)).getBody();
            userPage.getContent().forEach(u -> users.put(u.getUserLogin(),u));
        }
    }

    private void getOrders(){
        orderListForEachUser = new LinkedList<>();
        for (CustomerDto user: users.values()) {
            orderListForEachUser.addAll(oneCAdapterClient.getOrders(user.getUserLogin()).getBody());
        }
    }

    private Duration getDaysDependingOnCustomerType(CustomerType customerType){
        return Duration.ofDays(Long.parseLong(directoryInfo
                .getValues().get(customerType.toString())
                .substring(0,2)));
    }
}
