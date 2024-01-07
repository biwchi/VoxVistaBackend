package com.biwhci.vistaback.user.contollers;

import com.biwhci.vistaback.user.dtos.AppUserDto;
import com.biwhci.vistaback.user.services.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final AppUserService appUserService;

    @GetMapping
    public List<AppUserDto> getAllUsers() {
        return appUserService.findAllUsers();
    }
}
