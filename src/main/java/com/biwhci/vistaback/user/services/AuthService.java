package com.biwhci.vistaback.user.services;

import com.biwhci.vistaback.user.dtos.TokenResponse;
import com.biwhci.vistaback.user.dtos.AppUserCreateDto;
import com.biwhci.vistaback.user.dtos.AppUserDto;
import com.biwhci.vistaback.user.dtos.AppUserLoginDto;
import com.biwhci.vistaback.user.mappers.AppUserMapper;
import com.biwhci.vistaback.user.models.AppUser;
import com.biwhci.vistaback.user.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final AppUserService appUserService;
  private final AuthenticationManager authenticationManager;
  private final AppUserMapper appUserMapper;
  private final JwtUtils jwtUtils;

  public TokenResponse login(@RequestBody AppUserLoginDto user) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              user.email(),
              user.password()
          )
      );
    } catch (BadCredentialsException e) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
    }

    return new TokenResponse(getToken(user.email()));
  }

  public TokenResponse register(AppUserCreateDto newUser) {
    if (appUserService.findByEmail(newUser.getEmail()).isPresent()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists");
    }

    appUserService.createUser(newUser);

    return new TokenResponse(getToken(newUser.getEmail()));
  }

  private String getToken(String email) {
    UserDetails userDetails = appUserService.loadUserByUsername(email);
    return jwtUtils.generateToken(userDetails);
  }

  public AppUserDto getUser() {
    Optional<AppUser> user = appUserService.getCurrentUser();

    if(user.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    return appUserMapper.toDto(user.get());
  }
}
