package com.projects.ecomm.Controller;

import com.projects.ecomm.DTO.OrderResponse;
import com.projects.ecomm.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController
{
    private final OrderService service;

    @PostMapping("/createOrder/{userId}")
    public ResponseEntity<OrderResponse>createOrder(@PathVariable Long userId)
    {
        return service.createOrder(userId);
    }

    @PutMapping("/cancelOrder/{userId}/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable Long userId, @PathVariable Long orderId)
    {
        service.cancelOrder(userId,orderId);
        return new ResponseEntity<>("Order canceled successfully", HttpStatus.OK);
    }
}
