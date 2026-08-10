package com.projects.ecomm.CustomExceptions;

public class CartIsEmptyException extends RuntimeException
{
    public CartIsEmptyException(String message)
    {
        super(message);
    }
}
