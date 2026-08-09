package com.projects.ecomm.Service;

import com.projects.ecomm.DTO.ProductRequest;
import com.projects.ecomm.DTO.ProductResponse;
import com.projects.ecomm.Model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    ResponseEntity<List<ProductResponse>> getAllProducts();

    ResponseEntity<ProductResponse> getProductById(Long id);

    ResponseEntity<String> addProduct(ProductRequest productRequest);

    ResponseEntity<String> deleteProduct(Long id);
}
