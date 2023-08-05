package com.nebula.blogapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nebula.blogapp.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment,Integer>{
    
}
