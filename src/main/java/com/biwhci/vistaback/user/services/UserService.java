package com.biwhci.vistaback.user.services;

import com.biwhci.vistaback.poll.models.PollOption;
import com.biwhci.vistaback.user.dtos.UserCreateDto;
import com.biwhci.vistaback.user.dtos.UserDto;
import com.biwhci.vistaback.user.models.User;
import com.biwhci.vistaback.user.repositories.UserRepository;
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
public class UserService implements UserDetailsService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public Optional<UserDto> getCurrentUser() {
    Optional<UserDto> user = Optional.empty();
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if (principal instanceof UserDto) {
      user = Optional.of((UserDto) principal);
    }

    return user;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

    return new org.springframework.security.core.userdetails.User(
        user.getEmail(),
        user.getPassword(),
        user.getAuthorities()
    );
  }

  public List<UserDto> findAllUsers() {
    return userRepository.findAll().stream().map(UserDto::new).toList();
  }

  public Optional<User> findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  public Optional<User> findUserByUserIdAndVotedPollOptionId(Integer userId, Integer pollOptionId) {
    return userRepository.findUserByUserIdAndVotedPollOptionId(userId, pollOptionId);
  }

  public void createUser(UserCreateDto newUser) {
    User user = new User();

    user.setNickname(newUser.getNickname());
    user.setEmail(newUser.getEmail());
    user.setPassword(passwordEncoder.encode(newUser.getPassword()));

    userRepository.save(user);
  }
}
