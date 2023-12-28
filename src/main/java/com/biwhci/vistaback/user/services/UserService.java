package com.biwhci.vistaback.user.services;

import com.biwhci.vistaback.user.dots.UserCreateDto;
import com.biwhci.vistaback.user.dots.UserDto;
import com.biwhci.vistaback.user.models.User;
import com.biwhci.vistaback.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getAuthorities()
        );
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void createUser(UserCreateDto newUser) {
        User user = new User();

        user.setUsername(newUser.getUsername());
        user.setEmail(newUser.getEmail());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));

        userRepository.save(user);
    }

    public List<UserDto> findAllUsers() {
        return userRepository.findAll().stream().map(entity ->
                new UserDto(
                        entity.getUsername(),
                        entity.getEmail())
        ).toList();
    }
}
