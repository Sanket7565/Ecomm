package com.projects.ecomm.Controller;

import com.projects.ecomm.DTO.OrderResponse;
import com.projects.ecomm.Service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aoi/orders")
public class OrderController
{
    private OrderService service;

    @PostMapping("/createOrder/{userId}")
    public ResponseEntity<OrderResponse>createOrder(@PathVariable Long userId)
    {
        return service.createOrder(userId);
    }




}
