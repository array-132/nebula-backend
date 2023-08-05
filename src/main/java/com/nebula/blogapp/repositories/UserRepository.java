package com.nebula.blogapp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nebula.blogapp.entities.User;
public interface UserRepository extends JpaRepository<User,Integer>
{    
    Optional<User> findByEmail(String emailId);
}