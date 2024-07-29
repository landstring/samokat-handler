package com.example.samokathandler.services;

import com.example.samokathandler.entities.product.Product;
import com.example.samokathandler.exceptions.product.ProductNotFoundException;
import com.example.samokathandler.repositories.product.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;


    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(ProductNotFoundException::new);

    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }
}
