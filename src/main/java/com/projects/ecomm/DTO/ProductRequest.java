package com.projects.ecomm.DTO;

import com.projects.ecomm.Model.ProductStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class ProductRequest
{
    private String name;
    private String description;
    private Double price;
    private String category;
    private String imageUrl;
    private Integer stockQuantity;
}
