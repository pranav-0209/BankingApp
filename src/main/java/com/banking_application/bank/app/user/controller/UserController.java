package com.banking_application.bank.app.user.controller;

import com.banking_application.bank.app.user.dto.UserRequestDTO;
import com.banking_application.bank.app.user.dto.UserResponseDTO;
import com.banking_application.bank.app.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id){
        UserResponseDTO dto = userService.getUserById(id);
        return ResponseEntity.ok(dto);
    }


}
