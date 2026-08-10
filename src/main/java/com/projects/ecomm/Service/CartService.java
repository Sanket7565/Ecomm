package com.projects.ecomm.Service;

import com.projects.ecomm.DTO.CartItemRequest;
import com.projects.ecomm.DTO.CartItemResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartService {

    ResponseEntity<String> addTocart(Long userId, CartItemRequest cartItemRequest);

    ResponseEntity<String> deleteItemFromCart(Long userId, Long productId);

    ResponseEntity<List<CartItemResponse>> getAllItemsInCart(Long userId);
}
