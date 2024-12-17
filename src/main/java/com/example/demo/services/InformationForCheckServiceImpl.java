package com.example.demo.services;

import com.example.demo.exceptions.NoDataToVerifyException;
import com.example.demo.models.InformationForCheck;
import com.example.demo.remote.clients.ContentAdminClient;
import javacode.prac.api.client.feign.adapter.customer.CustomerServiceClient;
import javacode.prac.api.client.feign.adapter.customer.dto.CustomerDto;
import javacode.prac.api.client.feign.adapter.onec.OneCAdapterClient;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import payment.OrderEntityDto;

import java.util.*;

@Service
public class InformationForCheckServiceImpl implements InformationForCheckService {
    private final ContentAdminClient contentAdminClient;
    private final CustomerServiceClient customerServiceClient;
    private final OneCAdapterClient oneCAdapterClient;

    public InformationForCheckServiceImpl(ContentAdminClient contentAdminClient, CustomerServiceClient customerServiceClient, OneCAdapterClient oneCAdapterClient) {
        this.contentAdminClient = contentAdminClient;
        this.customerServiceClient = customerServiceClient;
        this.oneCAdapterClient = oneCAdapterClient;
    }

    @Cacheable(value = "checkInfo", unless = "#result == null")
    public InformationForCheck getInformationForVerification() throws NoDataToVerifyException {
        InformationForCheck resultInfo = new InformationForCheck();

        resultInfo.setDictionary(contentAdminClient.getDictionaryByName("PAYMENT_DEADLINE").getBody());
        if (resultInfo.getDictionary() == null) throw new NoDataToVerifyException();

        resultInfo.setUsers(getUsers());
        resultInfo.setOrders(getOrders(resultInfo.getUsers().values()));

        return resultInfo;
    }

    private Map<String, CustomerDto> getUsers() throws NoDataToVerifyException {
        Map<String, CustomerDto> users = new HashMap<>();
        int userPageSize = 50;

        Page<CustomerDto> userPage = customerServiceClient.getCustomers(PageRequest.of(0,userPageSize)).getBody();
        userPage.getContent().forEach(u -> users.put(u.getUserLogin(),u));

        while (userPage.hasNext()) {
            userPage = customerServiceClient.getCustomers(PageRequest.of((userPage.getNumber()+1),userPageSize)).getBody();
            userPage.getContent().forEach(u -> users.put(u.getUserLogin(),u));
        }

        if (userPage.isEmpty()) throw new NoDataToVerifyException();

        return users;
    }

    private List<OrderEntityDto> getOrders(Collection<CustomerDto> users) throws NoDataToVerifyException {
        List<OrderEntityDto> orders = new LinkedList<>();
        for (CustomerDto user: users) {
            orders.addAll(oneCAdapterClient.getOrders(user.getUserLogin()).getBody());
        }

        if (orders.isEmpty()) throw new NoDataToVerifyException();

        return orders;
    }
}
