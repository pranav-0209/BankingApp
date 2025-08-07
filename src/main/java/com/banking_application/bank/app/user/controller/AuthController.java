package com.banking_application.bank.app.user.controller;

import com.banking_application.bank.app.jwt.JwtUtil;
import com.banking_application.bank.app.user.dto.AuthRequestDTO;
import com.banking_application.bank.app.user.dto.AuthResponseDTO;
import com.banking_application.bank.app.user.dto.UserRequestDTO;
import com.banking_application.bank.app.user.model.User;
import com.banking_application.bank.app.user.service.UserDetailsServiceImpl;
import com.banking_application.bank.app.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {


    private final UserService userService;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody @Valid UserRequestDTO dto){
        userService.registerUser(dto);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> createAuthenticationToken(
            @Valid @RequestBody AuthRequestDTO authRequest) throws Exception {

        try {
            // Authenticate the user using Spring Security's AuthenticationManager
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        // If authentication is successful, load UserDetails
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());

        // Generate JWT token
        final String jwt = jwtUtil.generateToken(userDetails);

        // Return the token in the response DTO
        return ResponseEntity.ok(new AuthResponseDTO(jwt));
    }

}
