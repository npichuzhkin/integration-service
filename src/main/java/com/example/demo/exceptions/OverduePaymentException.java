package com.example.demo.exceptions;

public class OverduePaymentException extends Exception{
    public OverduePaymentException (String msg){
        super(msg);
    }
}
