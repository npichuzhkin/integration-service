package com.example.demo;

import com.example.demo.remoteClients.ContentAdminClient;
import javacode.prac.api.client.feign.adapter.customer.CustomerServiceClient;
import javacode.prac.api.client.feign.adapter.onec.OneCAdapterClient;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.cloud.openfeign.EnableFeignClients;
@SpringBootApplication
@EnableFeignClients(basePackageClasses = {ContentAdminClient.class, CustomerServiceClient.class, OneCAdapterClient.class})
public class OverduePaymentsApp {
	public static void main(String[] args) {
		SpringApplication.run(OverduePaymentsApp.class, args);
	}
}
