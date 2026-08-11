package com.projects.ecomm.Service;

import com.projects.ecomm.DTO.OrderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface OrderService
{

    ResponseEntity<OrderResponse> createOrder(Long userId);

    ResponseEntity<String> cancelOrder(Long userId, Long orderId);
}
