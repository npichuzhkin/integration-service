package com.example.demo.exceptions;

public class NoDataToVerifyException extends OverduePaymentException {
    public NoDataToVerifyException(){
        super("Failed to obtain verification data. Please try again later.");
    }
}
