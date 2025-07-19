package com.banking_application.bank.app.user.mapper;

import com.banking_application.bank.app.user.dto.UserRequestDTO;
import com.banking_application.bank.app.user.dto.UserResponseDTO;
import com.banking_application.bank.app.user.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    // Convert UserRequestDTO to User entity
    public static User toEntity(UserRequestDTO dto){
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setPassword(dto.getPassword());

        return user;
    }

    // Convert User entity to UserResponseDTO
    public static UserResponseDTO toDTO(User user){
        return new UserResponseDTO(
                user.getUid(),
                user.getName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getRoles(),
                user.getCreatedAt());
    }


}
