package com.example.demo.exceptions;

public class UnknownPaymentTermException extends OverduePaymentException {
    public UnknownPaymentTermException(){
        super("It was not possible to obtain the number of days allocated for payment.");
    }
}
