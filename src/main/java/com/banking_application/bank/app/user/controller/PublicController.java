package com.banking_application.bank.app.user.controller;

import com.banking_application.bank.app.user.dto.UserRequestDTO;
import com.banking_application.bank.app.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class PublicController {


    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserRequestDTO dto){
        userService.registerUser(dto);
        return ResponseEntity.ok("User registered successfully");
    }

}
