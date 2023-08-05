package com.nebula.blogapp.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nebula.blogapp.entities.Category;
import com.nebula.blogapp.exceptions.ResourceNotFoundException;
import com.nebula.blogapp.payloads.CategoryDto;
import com.nebula.blogapp.repositories.CategoryRepository;
import com.nebula.blogapp.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = this.modelMapper.map(categoryDto,Category.class);
        Category addedCategory = this.categoryRepository.save(category);
        return this.modelMapper.map(addedCategory, CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category c = this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", " id ", categoryId));
        this.categoryRepository.delete(c);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = this.categoryRepository.findAll();
        List<CategoryDto> categoryDtos = categories.stream().map((c) ->this.modelMapper.map(c,CategoryDto.class)).collect(Collectors.toList());
        return categoryDtos;
    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {
        Category c = this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", " id ", categoryId));
        return this.modelMapper.map(c,CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", " id ", categoryId));
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        Category updatedCategory = this.categoryRepository.save(category);
        return this.modelMapper.map(updatedCategory,CategoryDto.class);
    }
    
}
