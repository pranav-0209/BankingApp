package com.banking_application.bank.app.user.controller;

import com.banking_application.bank.app.user.dto.UserRequestDTO;
import com.banking_application.bank.app.user.dto.UserResponseDTO;
import com.banking_application.bank.app.user.model.User;
import com.banking_application.bank.app.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    @Autowired
    private UserService userService;

    @PostMapping("/create-admin-user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> createAdmin(@RequestBody UserRequestDTO userRequestDTO) {
        userService.saveAdmin(userRequestDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @GetMapping("/all-users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDTO>> getAllUser() {
        List<UserResponseDTO> users = userService.getAllUser();

        if (users == null || users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }




}
