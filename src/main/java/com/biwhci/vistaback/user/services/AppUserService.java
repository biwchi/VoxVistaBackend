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
  private final PasswordEncoder passwordEncoder;
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
    return appUserRepository.findAll().stream().map(AppUserDto::new).toList();
  }

  public Optional<AppUser> findByEmail(String email) {
    return appUserRepository.findByEmail(email);
  }

  public Optional<AppUser> findUserByUserIdAndVotedPollOptionId(Integer userId, Integer pollOptionId) {
    return appUserRepository.findUserByUserIdAndVotedPollOptionId(userId, pollOptionId);
  }

  public void createUser(AppUserCreateDto newUser) {
    AppUser user = new AppUser();

    user.setNickname(newUser.getNickname());
    user.setEmail(newUser.getEmail());
    user.setPassword(passwordEncoder.encode(newUser.getPassword()));

    appUserRepository.save(user);
  }
}
