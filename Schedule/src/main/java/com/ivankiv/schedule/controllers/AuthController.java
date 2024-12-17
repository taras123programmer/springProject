package com.ivankiv.schedule.controllers;

import com.ivankiv.schedule.JwtUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUntil jwtUntil;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtUntil jwtUntil) {
        this.authenticationManager = authenticationManager;
        this.jwtUntil = jwtUntil;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        try {
            // Перевірка логіну та пароля
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password())
            );

            // Генерація JWT токену для користувача
            String token = jwtUntil.generateToken(authRequest.username());
            return new AuthResponse(token);
        } catch (BadCredentialsException ex) {
            return new AuthResponse(null);
        }
    }

    record AuthResponse(String token) {};

    record AuthRequest(String username, String password) {};


}
