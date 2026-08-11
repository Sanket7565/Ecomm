package com.projects.ecomm.CustomExceptions;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String s)
    {
        super(s);
    }
}
