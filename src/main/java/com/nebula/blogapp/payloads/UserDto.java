package com.nebula.blogapp.payloads;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private int userId;
    
    @NotNull
    @NotBlank
    @Size(min = 4,message = "Username must be min of 4 characters")
    private String name;
    
    @Email(message = "Invalid Email")
    @NotBlank
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$", message = "Invalid email")
    private String email;
    
    @NotNull
    @NotBlank
    @Size(min = 6, max=16,message = "Password should have min 6 & and max 10 characters ")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[~!@#$%^&*])[a-zA-Z0-9!~@#$%^&*]{6,16}$", message = "Password should have atleast one letter,number,and atleast one of ~,!,@,#,$,%,^,&,*.")
    private String password;

    @NotNull
    @NotBlank
    private String about;

    @JsonIgnore
    public String getPassword(){
        return this.password;
    }
}