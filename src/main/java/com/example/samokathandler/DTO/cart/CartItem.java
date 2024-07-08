package com.example.samokathandler.DTO.cart;

import com.example.samokathandler.DTO.product.ProductDto;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CartItem {
    public ProductDto product;
    public Integer count;

    public CartItem(ProductDto product, Integer count){
        this.product = product;
        this.count = count;
    }

}
