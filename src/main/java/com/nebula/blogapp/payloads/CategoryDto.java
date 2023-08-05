package com.nebula.blogapp.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
    
    private Integer categoryId;
    @NotEmpty
    @Size(max = 30 , message = "Category title cannot exceed 30 chars")
    private String categoryTitle;
    @NotEmpty
    @Size(min = 8 , max = 150 , message = "Description should be between 8 and 150 characters")
    private String categoryDescription;
}
