package com.nebula.blogapp.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nebula.blogapp.entities.Category;
import com.nebula.blogapp.entities.Post;
import com.nebula.blogapp.entities.User;

public interface PostRepository extends JpaRepository<Post,Integer>{
    Page<Post> findByUser(User user,Pageable pageable);
    Page<Post> findByCategory(Category category,Pageable pageable);
    Page<Post> findByContentContaining(String keyword,Pageable pageable);
}
