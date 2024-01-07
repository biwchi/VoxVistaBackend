package com.biwhci.vistaback.configs;

import com.biwhci.vistaback.user.models.AppUser;
import com.biwhci.vistaback.user.repositories.AppUserRepository;
import com.biwhci.vistaback.user.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
  private final JwtUtils jwtUtils;
  private final AppUserRepository appUserRepository;

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain
  ) throws ServletException, IOException {
    String authHeader = request.getHeader("Authorization");
    String jwt = null;
    Claims user = null;

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    jwt = authHeader.substring(7);

    try {
      user = jwtUtils.getClaimsFromToken(jwt);
    } catch (ExpiredJwtException | SignatureException e) {
      filterChain.doFilter(request, response);
      return;
    }

    if (!user.isEmpty() && SecurityContextHolder.getContext().getAuthentication() == null) {
      Optional<AppUser> appUser = appUserRepository.findByEmail(user.get("email", String.class));

      if (appUser.isEmpty()) {
        filterChain.doFilter(request, response);
        return;
      }

      UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
          appUser.get(),
          null,
          new ArrayList<>()
      );

      SecurityContextHolder.getContext().setAuthentication(token);
    }

    filterChain.doFilter(request, response);
  }
}
