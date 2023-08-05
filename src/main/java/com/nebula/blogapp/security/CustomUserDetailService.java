package com.nebula.blogapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nebula.blogapp.entities.User;
import com.nebula.blogapp.exceptions.ResourceNotFoundException;
import com.nebula.blogapp.repositories.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService{

    
    @Autowired
    public UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // loading user from database by username
        User user = this.userRepository.findByEmail(username).orElseThrow(()->new ResourceNotFoundException(" User","Entered email is : "+ username, 0));
        return (UserDetails)user;
    }
}