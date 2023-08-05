package com.nebula.blogapp.services;

import java.util.List;

import com.nebula.blogapp.payloads.CategoryDto;

public interface CategoryService {
    
    //create
    CategoryDto createCategory(CategoryDto categoryDto);

    //update
    CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);

    //delete
    void deleteCategory(Integer categoryId);

    //get
    CategoryDto getCategory(Integer categoryId);

    //get all
    List<CategoryDto> getAllCategories();
}
