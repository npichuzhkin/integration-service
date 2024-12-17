package com.example.demo.services;

import com.example.demo.exceptions.NoDataToVerifyException;
import com.example.demo.models.InformationForCheck;
import org.springframework.stereotype.Service;

@Service
public interface InformationForCheckService {
    InformationForCheck getInformationForVerification() throws NoDataToVerifyException;
}
