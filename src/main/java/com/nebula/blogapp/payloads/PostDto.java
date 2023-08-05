package com.nebula.blogapp.payloads;

import java.util.Date;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
    
    private Integer postId;
    
    @NotBlank
    @Size(min=2,max=50,message = "title should be between 2 and 50 characters")
    private String title;

    @NotBlank
    @Size(min=50,max=2500,message=" Content should be between 50 and 2500 characters")
    private String content;

    @NotNull
    private String imageName;

    private Date addedDate;
    
    private Date lastEditDate;
    
    private CategoryDto category;

    private UserDto user;

    private List<CommentDto> comments;
}