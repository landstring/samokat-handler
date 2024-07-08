package com.example.samokathandler.DTO.product;

import com.example.samokathandler.entities.product.Product;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductDto {
    public Long id;
    public String name;
    public String description;
    public Long price;
    public String productImage_url;
    public CategoryDto category;

    public ProductDto(Product product){
        id = product.getId();
        name = product.getName();
        description = product.getDescription();
        price = product.getPrice();
        productImage_url = product.getImage_url();
        category = new CategoryDto(product.getCategory());
    }
}
