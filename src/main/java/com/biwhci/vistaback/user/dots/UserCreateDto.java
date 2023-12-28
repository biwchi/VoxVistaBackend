package com.biwhci.vistaback.user.dots;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserCreateDto {

    @NotBlank(message = "Username cannot be empty")
    @Size(min = 3, message = "Username must be at least 3 characters")
    private final String username;

    @Email
    @NotBlank(message = "Email cannot be empty")
    private final String email;

    @Min(value = 6, message = "Password must be at least 6 characters")
    @NotBlank(message = "Password cannot be empty")
    private final String password;
}
