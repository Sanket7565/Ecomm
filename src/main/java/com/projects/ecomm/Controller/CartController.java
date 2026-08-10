package com.projects.ecomm.Controller;

import com.projects.ecomm.DTO.CartItemRequest;
import com.projects.ecomm.DTO.CartItemResponse;
import com.projects.ecomm.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController
{

    @Autowired
    CartService service;

    @PostMapping("/addItem/{userId}")
    public ResponseEntity<String> addTocart(@PathVariable Long userId, @RequestBody CartItemRequest cartItemRequest)
    {
       return service.addTocart(userId,cartItemRequest);
    }

    @DeleteMapping("/deleteItem/{userId}/{productId}")
    public ResponseEntity<String> deleteItemFromCart(@PathVariable Long userId, @PathVariable Long productId)
    {
        return service.deleteItemFromCart(userId, productId);
    }

    @GetMapping("getAllItems/{userId}")
    public ResponseEntity<List<CartItemResponse>> getAllItemsInCart(@PathVariable Long userId)
    {
        return service.getAllItemsInCart(userId);
    }
}
