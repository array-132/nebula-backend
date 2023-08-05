package com.nebula.blogapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nebula.blogapp.entities.Role;

public interface RoleRepository extends JpaRepository<Role,Integer>{
    
}
