package com.projects.ecomm.DTO;

import com.projects.ecomm.Model.OrderItems;
import com.projects.ecomm.Model.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderResponse
{
    private Long Id;
    private BigDecimal totalAmount;
    private List<OrderItemsDTO> items;
    private OrderStatus status;

}
