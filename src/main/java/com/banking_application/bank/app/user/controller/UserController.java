package com.banking_application.bank.app.user.controller;

import com.banking_application.bank.app.user.dto.UserRequestDTO;
import com.banking_application.bank.app.user.dto.UserResponseDTO;
import com.banking_application.bank.app.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    // Now, authentication.principal is your CustomUserDetails, so you can call getUserId()
    @PreAuthorize("#id == authentication.principal.userId or hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        // You can also access the authenticated user's ID within the method if needed
        // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        // Long authenticatedUserId = customUserDetails.getUserId();

        UserResponseDTO userResponseDTO = userService.getUserById(id);
        return ResponseEntity.ok(userResponseDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("#id == authentication.principal.userId or hasRole('ADMIN')")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable("id") Long id,
                                                      @RequestBody @Valid UserRequestDTO requestDTO) {

        UserResponseDTO updatedUser = userService.updateUser(id, requestDTO);

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

}
