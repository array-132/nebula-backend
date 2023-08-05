package com.nebula.blogapp.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
// import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nebula.blogapp.entities.User;
import com.nebula.blogapp.exceptions.ApiException;
import com.nebula.blogapp.payloads.JwtAuthRequest;
import com.nebula.blogapp.payloads.JwtAuthResponse;
import com.nebula.blogapp.payloads.UserDto;
import com.nebula.blogapp.security.JwtTokenHelper;
import com.nebula.blogapp.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    
    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;
    
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(
        @Valid @RequestBody JwtAuthRequest request
    ) throws Exception{
        authenticate(request.getEmail(),request.getPassword());
        
        UserDetails userDetails=this.userDetailsService.loadUserByUsername(request.getEmail());
        
        String token = this.jwtTokenHelper.generateToken(userDetails);
        
        JwtAuthResponse response = new JwtAuthResponse();
        
        response.setToken(token);
        response.setUser(this.modelMapper.map((User)userDetails,UserDto.class));
        return new ResponseEntity<JwtAuthResponse>(response,HttpStatus.OK);
    }

    private void authenticate(String username, String password) throws Exception{

        try{
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            System.out.println(authenticationToken);    
            this.authenticationManager.authenticate(authenticationToken);
        }catch(BadCredentialsException e){
            System.out.println("Invalid Details");
            throw new ApiException("Invalid password");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody UserDto userDto){

        UserDto userDto2 =this.userService.registerNewUser(userDto);
        return new ResponseEntity<UserDto>(userDto2,HttpStatus.CREATED);
    }
}