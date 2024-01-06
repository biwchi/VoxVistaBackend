package com.biwhci.vistaback.user.contollers;

import com.biwhci.vistaback.user.dtos.UserDto;
import com.biwhci.vistaback.user.models.User;
import com.biwhci.vistaback.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers();
    }
}
