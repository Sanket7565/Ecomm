package com.projects.ecomm.IMPL;

import com.projects.ecomm.CustomExceptions.CartIsEmptyException;
import com.projects.ecomm.CustomExceptions.UserNotFoundException;
import com.projects.ecomm.DTO.CartItemResponse;
import com.projects.ecomm.DTO.OrderResponse;
import com.projects.ecomm.Model.CartItem;
import com.projects.ecomm.Model.OrderStatus;
import com.projects.ecomm.Model.Orders;
import com.projects.ecomm.Repository.OrderRepo;
import com.projects.ecomm.Repository.UserRepo;
import com.projects.ecomm.Service.CartService;
import com.projects.ecomm.Service.OrderService;
import com.projects.ecomm.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceIMPL implements OrderService
{
    CartService cartService;
    UserService userService;
    UserRepo uRepo;
    OrderRepo oRepo;


    @Override
    public ResponseEntity<OrderResponse> createOrder(Long userId)
    {
        List<CartItemResponse> items= cartService.getAllItemsInCart(userId).getBody();

        if(items.isEmpty())
        {
            throw new CartIsEmptyException("User Cart is empty");
        }
        if(userService.getUserById(userId) == null)
        {
            throw new UserNotFoundException("User Not Found");
        }


        // calculate total price

        BigDecimal totalAmount =items.stream()
                .map(CartItemResponse::getTotalPrice)
                .reduce(BigDecimal.ZERO,BigDecimal::add);

        Orders orders = new Orders();

        orders.setUser(uRepo.findById(userId).get());
        orders.setTotalAmount(totalAmount);
        orders.setOrderStatus(OrderStatus.CONFIRMED);
        orders.setItems(items);

        oRepo.save(orders);
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setStatus(OrderStatus.CONFIRMED);
        orderResponse.setTotalAmount(totalAmount);
        orderResponse.setItems(items);

        return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);




    }
}
