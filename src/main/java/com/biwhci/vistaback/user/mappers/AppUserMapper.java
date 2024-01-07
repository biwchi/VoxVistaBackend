package com.biwhci.vistaback.user.mappers;

import com.biwhci.vistaback.user.dtos.AppUserCreateDto;
import com.biwhci.vistaback.user.dtos.AppUserDto;
import com.biwhci.vistaback.user.models.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppUserMapper {
  private final PasswordEncoder passwordEncoder;

  public AppUserDto toDto(AppUser appUser) {
    return new AppUserDto(appUser);
  }

  public AppUser create(AppUserCreateDto appUserCreateDto) {
    return new AppUser(
        appUserCreateDto.getNickname(),
        appUserCreateDto.getEmail(),
        passwordEncoder.encode(appUserCreateDto.getPassword())
    );
  }
}
