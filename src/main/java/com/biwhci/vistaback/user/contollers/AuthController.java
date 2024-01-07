package com.biwhci.vistaback.user.contollers;

import com.biwhci.vistaback.user.dtos.TokenResponse;
import com.biwhci.vistaback.user.dtos.AppUserCreateDto;
import com.biwhci.vistaback.user.dtos.AppUserDto;
import com.biwhci.vistaback.user.dtos.AppUserLoginDto;
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
    public TokenResponse login(@RequestBody AppUserLoginDto user) {
        return authService.login(user);
    }

    @PostMapping("/register")
    public TokenResponse register(@Valid @RequestBody AppUserCreateDto newUser) {
        return authService.register(newUser);
    }

    @PostMapping("/user")
    public AppUserDto getUser(HttpServletRequest request) {
        log.info(request.getHeader("Authorization"));
        return authService.getUser();
    }
}
