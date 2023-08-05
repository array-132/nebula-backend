package com.nebula.blogapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nebula.blogapp.entities.Category;

public interface CategoryRepository extends JpaRepository<Category,Integer>{
    
}
