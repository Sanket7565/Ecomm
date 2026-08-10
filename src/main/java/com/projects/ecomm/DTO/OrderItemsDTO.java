package com.projects.ecomm.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemsDTO
{
    private Long id;
    private Long productId;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal totalPrice;
}
