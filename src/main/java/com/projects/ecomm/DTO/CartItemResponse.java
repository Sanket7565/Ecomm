package com.projects.ecomm.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemResponse
{
    private String productName;
    private Integer quantity;
    private String category;
    private BigDecimal totalPrice;
    private String description;


}
