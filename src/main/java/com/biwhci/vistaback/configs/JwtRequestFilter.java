package com.biwhci.vistaback.configs;

import com.biwhci.vistaback.user.dtos.UserDto;
import com.biwhci.vistaback.user.services.UserService;
import com.biwhci.vistaback.user.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
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

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
  private final JwtUtils jwtUtils;
  private final UserService userService;

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
      UserDto userDto = new UserDto((Integer) user.get("id"), (String) user.get("nickname"), user.getSubject());

      UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
          userDto,
          null,
          new ArrayList<>()
      );

      SecurityContextHolder.getContext().setAuthentication(token);
    }

    filterChain.doFilter(request, response);
  }
}
