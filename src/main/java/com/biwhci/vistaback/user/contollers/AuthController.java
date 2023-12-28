package com.biwhci.vistaback.user.contollers;

import com.biwhci.vistaback.user.dots.TokenResponse;
import com.biwhci.vistaback.user.dots.UserCreateDto;
import com.biwhci.vistaback.user.dots.UserLoginDto;
import com.biwhci.vistaback.user.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public TokenResponse login(@RequestBody UserLoginDto user) {
        return authService.login(user);
    }

    @PostMapping("/register")
    public TokenResponse register(@Valid @RequestBody UserCreateDto newUser) {
        return authService.register(newUser);
    }
}
