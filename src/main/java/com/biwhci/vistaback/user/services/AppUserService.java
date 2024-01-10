package com.biwhci.vistaback.user.services;

import com.biwhci.vistaback.user.dtos.AppUserCreateDto;
import com.biwhci.vistaback.user.dtos.AppUserDto;
import com.biwhci.vistaback.user.mappers.AppUserMapper;
import com.biwhci.vistaback.user.models.AppUser;
import com.biwhci.vistaback.user.repositories.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppUserService implements UserDetailsService {
  private final AppUserRepository appUserRepository;
  private final AppUserMapper appUserMapper;

  public Optional<AppUser> getCurrentUser() {
    Optional<AppUser> user = Optional.empty();
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if (principal instanceof AppUser) {
      user = Optional.of((AppUser) principal);
    }

    return user;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    AppUser user = findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

    return new org.springframework.security.core.userdetails.User(
        user.getEmail(),
        user.getPassword(),
        user.getAuthorities()
    );
  }

  public List<AppUserDto> findAllUsers() {
    return appUserRepository.findAll().stream().map(appUserMapper::toDto).toList();
  }

  public List<AppUserDto> findUsersByVotedPollOptionId(Integer pollOptionId) {
    return appUserRepository.findByVotedPollOptionId(pollOptionId).stream().map(appUserMapper::toDto).toList();
  }

  public Optional<AppUser> findByEmail(String email) {
    return appUserRepository.findByEmail(email);
  }

  public void createUser(AppUserCreateDto newUser) {
    AppUser user = appUserMapper.create(newUser);
    appUserRepository.save(user);
  }
}
