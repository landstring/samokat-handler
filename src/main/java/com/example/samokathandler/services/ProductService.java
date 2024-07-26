package com.example.samokathandler.services;

import com.example.samokathandler.DTO.product.CategoryDto;
import com.example.samokathandler.DTO.product.ProductDto;
import com.example.samokathandler.entities.product.Category;
import com.example.samokathandler.entities.product.Product;
import com.example.samokathandler.exceptions.product.CategoryNotFoundException;
import com.example.samokathandler.exceptions.product.ProductNotFoundException;
import com.example.samokathandler.repositories.product.CategoryRepository;
import com.example.samokathandler.repositories.product.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ProductDto getProductById(Long id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        Product product;
        if (optionalProduct.isPresent()){
            product = optionalProduct.get();
        }
        else{
            throw new ProductNotFoundException();
        }
        return new ProductDto(product);
    }
}
