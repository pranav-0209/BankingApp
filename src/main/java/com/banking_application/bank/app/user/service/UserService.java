package com.banking_application.bank.app.user.service;

import com.banking_application.bank.app.user.dto.UserRequestDTO;
import com.banking_application.bank.app.user.dto.UserResponseDTO;
import com.banking_application.bank.app.user.mapper.UserMapper;
import com.banking_application.bank.app.user.model.Role;
import com.banking_application.bank.app.user.model.User;
import com.banking_application.bank.app.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {



    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(UserRequestDTO dto){
        // Map DTO to Entity
        User user = UserMapper.toEntity(dto);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Set default role if not already provided
        if(user.getRoles() == null || user.getRoles().isEmpty()){
            user.setRoles(Set.of(Role.CUSTOMER));
        }
        user.setCreatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }


    public UserResponseDTO getUserById(Long id){
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("User does not exist"));

        return UserMapper.toDTO(user);
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public void saveAdmin(UserRequestDTO dto){
        // Map DTO to Entity
        User user = UserMapper.toEntity(dto);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Set default role if not already provided
        if(user.getRoles() == null || user.getRoles().isEmpty()){
            user.setRoles(Set.of(Role.CUSTOMER));
        }
        user.setCreatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

}
