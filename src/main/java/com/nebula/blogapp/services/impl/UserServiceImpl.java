package com.nebula.blogapp.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nebula.blogapp.config.AppConstants;
import com.nebula.blogapp.entities.Role;
import com.nebula.blogapp.entities.User;
import com.nebula.blogapp.exceptions.*;
import com.nebula.blogapp.payloads.UserDto;
import com.nebula.blogapp.repositories.RoleRepository;
import com.nebula.blogapp.repositories.UserRepository;
import com.nebula.blogapp.services.UserService;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;
    
    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
        User savedUser = this.userRepository.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User"," id ",userId));
        this.userRepository.delete(user);        
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = this.userRepository.findAll();
        List<UserDto> userDtos = users.stream().map((user)->this.userToDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        
        User user = this.userRepository
                    .findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User"," id ",userId));
        
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
        
        User updatedUser = this.userRepository.save(user);
        return this.userToDto(updatedUser);
    }
    
    
    private User dtoToUser(UserDto userDto){
        User user = this.modelMapper.map(userDto,User.class);
        // user.setUser_id(userDto.getUser_id());
        // user.setName(userDto.getName());
        // user.setEmail(userDto.getEmail());
        // user.setPassword(userDto.getPassword());
        // user.setAbout(userDto.getAbout());
        return user; 
    }

    private UserDto userToDto(User user){
        UserDto userDto = this.modelMapper.map(user,UserDto.class);
        // userDto.setUser_id(user.getUser_id());
        // userDto.setName(user.getName());
        // userDto.setEmail(user.getEmail());
        // userDto.setPassword(user.getPassword());
        // userDto.setAbout(user.getAbout());
        return userDto; 
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user = this.userRepository
                        .findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("User", " Id", userId));
        return this.userToDto(user);
    }

    @Override
    public UserDto registerNewUser(UserDto userDto) {
        
        User user = this.modelMapper.map(userDto,User.class);

        // encoded the password
        user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
        
        // set roles to new user
        Role role = this.roleRepository.findById(AppConstants.ROLE_NORMAL).get();
        user.getRoles().add(role);
        User newUser = this.userRepository.save(user);

        return this.modelMapper.map(newUser,UserDto.class);
    }

    // @Override
    // public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    //     throw new UnsupportedOperationException("Unimplemented method 'loadUserByUsername'");
    // }
}
