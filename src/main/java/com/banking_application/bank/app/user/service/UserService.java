package com.banking_application.bank.app.user.service;

import com.banking_application.bank.app.user.dto.UserRequestDTO;
import com.banking_application.bank.app.user.dto.UserResponseDTO;
import com.banking_application.bank.app.user.mapper.UserMapper;
import com.banking_application.bank.app.user.model.Role;
import com.banking_application.bank.app.user.model.User;
import com.banking_application.bank.app.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    public void deleteUser(Long id){
        if(!userRepository.existsById(id)){
            throw new RuntimeException("User with ID " + id + " not found for deletion.");
        }
        userRepository.deleteById(id);
    }

    public User findByUserName(String username){
        return userRepository.findByName(username);
    }


    public void saveAdmin(UserRequestDTO dto){
        // Map DTO to Entity
        User user = UserMapper.toEntity(dto);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Set<Role> roles = new HashSet<>();
        roles.add(Role.ADMIN);
        roles.add(Role.CUSTOMER);
        user.setRoles(roles);
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);
    }

    public List<UserResponseDTO> getAllUser() {
        return userRepository.findAll().stream()
                .map(UserMapper::toDTO) // Map each User entity to a UserResponseDTO
                .collect(Collectors.toList());
    }

    public UserResponseDTO updateUser(Long id, @Valid UserRequestDTO requestDTO) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User with ID " + id + " not found for update."));

        if (requestDTO.getName() != null && !requestDTO.getName().isEmpty()) {
            existingUser.setName(requestDTO.getName());
        }
        if (requestDTO.getEmail() != null && !requestDTO.getEmail().isEmpty()) {
            existingUser.setEmail(requestDTO.getEmail());
        }
        if (requestDTO.getPhoneNumber() != null && !requestDTO.getPhoneNumber().isEmpty()) {
            existingUser.setPhoneNumber(requestDTO.getPhoneNumber());
        }

        User updatedUser = userRepository.save(existingUser);

        return UserMapper.toDTO(updatedUser);
    }


}
