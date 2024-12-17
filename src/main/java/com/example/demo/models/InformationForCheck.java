package com.example.demo.models;

import com.example.demo.dto.DictionaryRequestDTO;
import javacode.prac.api.client.feign.adapter.customer.dto.CustomerDto;
import lombok.Data;
import payment.OrderEntityDto;

import java.util.List;
import java.util.Map;

@Data
public class InformationForCheck {
    private DictionaryRequestDTO dictionary;
    private Map<String, CustomerDto> users;
    private List<OrderEntityDto> orders;
}
