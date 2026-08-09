package com.projects.ecomm.IMPL;

import com.projects.ecomm.DTO.ProductRequest;
import com.projects.ecomm.DTO.ProductResponse;
import com.projects.ecomm.Model.Product;
import com.projects.ecomm.Model.ProductStatus;
import com.projects.ecomm.Repository.ProductRepo;
import com.projects.ecomm.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
@Primary
public class ProductServiceIMPL implements ProductService
{
    @Autowired
    ProductRepo repo;

    @Override
    public ResponseEntity<List<ProductResponse>> getAllProducts()
    {
        List<ProductResponse> productResponses = new ArrayList<>();
        List<Product> products = repo.findAll();

        for (Product product : products)
        {
            ProductResponse productResponse = new ProductResponse();
            productResponse.setName(product.getName());
            productResponse.setDescription(product.getDescription());
            productResponse.setPrice(product.getPrice());
            productResponse.setCategory(product.getCategory());
            productResponse.setStatus(product.getStatus());
            productResponses.add(productResponse);
        }
        return new  ResponseEntity<List<ProductResponse>>(productResponses, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ProductResponse> getProductById(Long id) {
        if(repo.findById(Math.toIntExact(id)).isPresent())
        {
            //return new ResponseEntity<>(repo.findById(Math.toIntExact(id)).get(), HttpStatus.OK);

            Product product = repo.findById(Math.toIntExact(id)).get();
            ProductResponse productResponse = new ProductResponse();
            productResponse.setName(product.getName());
            productResponse.setDescription(product.getDescription());
            productResponse.setPrice(product.getPrice());
            productResponse.setCategory(product.getCategory());
            productResponse.setStatus(product.getStatus());
            return new ResponseEntity<>(productResponse, HttpStatus.OK);
        }
        // return a typed empty 404 response to avoid ambiguous null generic inference
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @Override
    public ResponseEntity<String> addProduct(ProductRequest productRequest)
    {

        Product product = new Product();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setCategory(productRequest.getCategory());
        product.setStatus(ProductStatus.ACTIVE);
        repo.save(product);
        return new ResponseEntity<>("Product with ID" +product.getId()+"Added Successfully", HttpStatus.CREATED);
    }


    @Override
    public ResponseEntity<String> deleteProduct(Long id) {

        if(repo.findById(Math.toIntExact(id)).isPresent())
        {
            repo.deleteById(Math.toIntExact(id));
            return new ResponseEntity<>("Product with ID " + id + " deleted successfully", HttpStatus.GONE);
        }
        return new ResponseEntity<>("Product Not Found", HttpStatus.NOT_FOUND);
    }
}
