package com.biwhci.vistaback.user.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserCreateDto {

  @NotBlank(message = "Nickname cannot be empty")
  @Size(min = 3, message = "Nickname must be at least 3 characters")
  private final String nickname;

  @Email
  @NotBlank(message = "Email cannot be empty")
  private final String email;

  @Size(min = 6, message = "Password must be at least 6 characters")
  @NotBlank(message = "Password cannot be empty")
  private final String password;
}
