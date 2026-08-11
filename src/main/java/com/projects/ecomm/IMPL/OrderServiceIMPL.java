package com.projects.ecomm.IMPL;

import com.projects.ecomm.CustomExceptions.CartIsEmptyException;
import com.projects.ecomm.CustomExceptions.OrderNotFoundException;
import com.projects.ecomm.CustomExceptions.UserNotFoundException;
import com.projects.ecomm.DTO.CartItemResponse;
import com.projects.ecomm.DTO.OrderItemsDTO;
import com.projects.ecomm.DTO.OrderResponse;
import com.projects.ecomm.Model.CartItem;
import com.projects.ecomm.Model.OrderItems;
import com.projects.ecomm.Model.OrderStatus;
import com.projects.ecomm.Model.Orders;
import com.projects.ecomm.Repository.CartRepo;
import com.projects.ecomm.Repository.OrderRepo;
import com.projects.ecomm.Repository.UserRepo;
import com.projects.ecomm.Service.CartService;
import com.projects.ecomm.Service.OrderService;
import com.projects.ecomm.Service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceIMPL implements OrderService
{
    private final CartService cartService;
    private final UserService userService;
    private final UserRepo uRepo;
    private final OrderRepo oRepo;
    private final CartRepo cRepo;


    @Transactional
    @Override
    public ResponseEntity<OrderResponse> createOrder(Long userId)
    {
        List<CartItem> items= cRepo.findByUser(uRepo.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId)));

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
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO,BigDecimal::add);

        Orders orders = new Orders();

        orders.setUser(uRepo.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId)));
        orders.setTotalAmount(totalAmount);
        orders.setOrderStatus(OrderStatus.CONFIRMED);



        List<OrderItems> orderItems = items.stream()
                .map(item -> new OrderItems(null,item.getProduct(), item.getQuantity(), item.getPrice(), orders))
                .collect(Collectors.toList());

        orders.setItems(orderItems);

        Orders saveOrders = oRepo.save(orders);

        // Clear the cart after creating the order
        cartService.clearCart(userId);

        return new ResponseEntity<>(convertToOrderResponse(saveOrders), HttpStatus.CREATED);

    }



    private OrderResponse convertToOrderResponse(Orders saveOrders) {
        // Implementation for converting Orders to OrderResponse
        return new OrderResponse(
            saveOrders.getId(),
            saveOrders.getTotalAmount(),
            saveOrders.getItems().stream()
                .map(item -> new OrderItemsDTO(
                    item.getId(),
                    item.getProduct().getId(),
                    item.getQuantity(),
                    item.getPrice()
                )).collect(Collectors.toList()),
            saveOrders.getOrderStatus()
        );
    }

    @Override
    public ResponseEntity<String> cancelOrder(Long userId, Long orderId)
    {
        if(!uRepo.existsById(userId))
        {
            throw new UserNotFoundException("User not found with id: " + userId);
        }
        if(!oRepo.existsById(Math.toIntExact(orderId)))
        {
            throw new OrderNotFoundException("Order not found with id: " + orderId);
        }

        Orders order = oRepo.findById(Math.toIntExact(orderId)).orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));
        order.setOrderStatus(OrderStatus.CANCELLED);
        oRepo.save(order);
        return new ResponseEntity<>("Order canceled successfully", HttpStatus.OK);
    }
}
