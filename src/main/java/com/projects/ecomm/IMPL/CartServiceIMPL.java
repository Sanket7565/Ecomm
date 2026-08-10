package com.projects.ecomm.IMPL;

import com.projects.ecomm.CustomExceptions.ProductNotFoundException;
import com.projects.ecomm.CustomExceptions.UserNotFoundException;
import com.projects.ecomm.DTO.CartItemRequest;
import com.projects.ecomm.DTO.CartItemResponse;
import com.projects.ecomm.Model.CartItem;
import com.projects.ecomm.Model.Product;
import com.projects.ecomm.Model.User;
import com.projects.ecomm.Repository.CartRepo;
import com.projects.ecomm.Repository.ProductRepo;
import com.projects.ecomm.Repository.UserRepo;
import com.projects.ecomm.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceIMPL implements CartService

{
    @Autowired
    private CartRepo cRepo;

    @Autowired
    private UserRepo uRepo;

    @Autowired
    private ProductRepo pRepo;

    @Override
    public ResponseEntity<String> addTocart(Long userId, CartItemRequest cartItemRequest)
    {
        if(!uRepo.existsById(userId))
        {
            throw new UserNotFoundException("User not found with id: " + userId);
        }

        if (!pRepo.existsById(Math.toIntExact(cartItemRequest.getProductId())))
        {
            throw new ProductNotFoundException("Product not found with id: " + cartItemRequest.getProductId());
        }

        Product product =pRepo.findById(Math.toIntExact(cartItemRequest.getProductId())).orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + cartItemRequest.getProductId()));

        if(product.getStockQuantity() < cartItemRequest.getQuantity())
        {
            return ResponseEntity.badRequest().body("Insufficient stock for product: " + product.getName());
        }

        CartItem alreadyExists = cRepo.findByUserAndProduct(uRepo.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId)), product);

        if(alreadyExists != null)
        {
            alreadyExists.setQuantity(cartItemRequest.getQuantity() + alreadyExists.getQuantity());
            alreadyExists.setPrice(product.getPrice().multiply(new BigDecimal(alreadyExists.getQuantity())));
            cRepo.save(alreadyExists);

            //product.setStockQuantity(product.getStockQuantity() - alreadyExists.getQuantity());
            //pRepo.save(product);
            return ResponseEntity.ok("Item added to cart successfully");



             }


        else {



        User user = uRepo.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        CartItem cartItem = new CartItem();
        cartItem.setUser(user);
        cartItem.setQuantity(cartItemRequest.getQuantity());
        cartItem.setPrice(product.getPrice().multiply(new BigDecimal(cartItemRequest.getQuantity())));
        cartItem.setProduct(product);

            //product.setStockQuantity(product.getStockQuantity() - cartItem.getQuantity());
            //pRepo.save(product);

        cRepo.save(cartItem);

        return ResponseEntity.ok("Item added to cart successfully");
    }
    }

    @Override
    public ResponseEntity<String> deleteItemFromCart(Long userId, Long productId)
    {
        if(!uRepo.existsById(userId))
        {
            throw new UserNotFoundException("User not found with id: " + userId);
        }

        if (!pRepo.existsById(Math.toIntExact(productId)))
        {
            throw new ProductNotFoundException("Product not found with id: " + productId);
        }

        Optional<CartItem> item = Optional.ofNullable(cRepo.findByUserAndProduct(uRepo.findById(userId), pRepo.findById(Math.toIntExact(productId))));

        if (item.isPresent()) {
            cRepo.deleteById(Math.toIntExact(item.get().getId()));
        }
        return ResponseEntity.ok("Item deleted from cart successfully");
    }

    @Override
    public ResponseEntity<List<CartItemResponse>> getAllItemsInCart(Long userId)
    {
        if(!uRepo.existsById(userId))
        {
            throw new UserNotFoundException("User not found with id: " + userId);
        }
        List<CartItem> items = cRepo.findByUser(uRepo.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId)));
        List<CartItemResponse> cartItemResponses = new ArrayList<>();
        for (CartItem item : items)
        {

            CartItemResponse response = new CartItemResponse();
            response.setProductName(item.getProduct().getName());
            response.setQuantity(item.getQuantity());
            response.setTotalPrice(item.getPrice());
            response.setCategory(item.getProduct().getCategory().toString());
            response.setDescription(item.getProduct().getDescription());

            cartItemResponses.add(response);
        }
        return new ResponseEntity<>(cartItemResponses, HttpStatus.OK);
    }
}
