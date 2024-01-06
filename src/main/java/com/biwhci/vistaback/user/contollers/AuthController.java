package com.biwhci.vistaback.user.contollers;

import com.biwhci.vistaback.user.dtos.TokenResponse;
import com.biwhci.vistaback.user.dtos.UserCreateDto;
import com.biwhci.vistaback.user.dtos.UserDto;
import com.biwhci.vistaback.user.dtos.UserLoginDto;
import com.biwhci.vistaback.user.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
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

    @PostMapping("/user")
    public UserDto getUser(HttpServletRequest request) {
        log.info(request.getHeader("Authorization"));
        return authService.getUser();
    }
}
