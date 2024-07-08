package com.example.samokathandler.DTO.product;

import com.example.samokathandler.entities.product.Category;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class CategoryDto {
    Long id;
    String name;
    String categoryImage_url;
    List<CategoryDto> children;

    public CategoryDto(Category category){
        id = category.getId();
        name = category.getName();
        categoryImage_url = category.getImage_url();
        children = new ArrayList<>();
        for (Category child : category.getChildren()){
            children.add(new CategoryDto(child));
        }
    }
}
