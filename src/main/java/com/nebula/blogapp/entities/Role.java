package com.nebula.blogapp.entities;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Role {
    
    @Id
    private int roleId;

    private String name;

}
