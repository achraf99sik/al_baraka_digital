package com.al_baraka_digital.Baraka.service.impl;

import com.al_baraka_digital.Baraka.dto.AuthenticationRequest;
import com.al_baraka_digital.Baraka.dto.AuthenticationResponse;
import com.al_baraka_digital.Baraka.dto.RegisterRequest;
import com.al_baraka_digital.Baraka.enums.Role;
import com.al_baraka_digital.Baraka.exception.AlreadyExistsException;
import com.al_baraka_digital.Baraka.exception.ResourceNotFoundException;
import com.al_baraka_digital.Baraka.model.User;
import com.al_baraka_digital.Baraka.repository.UserRepository;
import com.al_baraka_digital.Baraka.service.AccountService;
import com.al_baraka_digital.Baraka.service.AuthenticationService;
import com.al_baraka_digital.Baraka.service.JwtService;
import com.al_baraka_digital.Baraka.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final AccountService accountService;

    @Override
    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new AlreadyExistsException("Email already taken");
        }

        var user = new User();
        user.setFullName(request.getFirstname() + ' ' + request.getLastname());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Role role = request.getRole() != null ? request.getRole() : Role.CLIENT;
        user.setRole(role);
        user.setActive(true);

        var savedUser = userRepository.save(user);

        if (role == Role.CLIENT) {
            accountService.createAccount(savedUser);
        }

        var jwtToken = jwtService.generateAccessToken(savedUser);
        var refreshToken = jwtService.generateRefreshToken(savedUser);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        String jwtToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AuthenticationResponse refreshToken(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtUtil.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.userRepository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtUtil.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateAccessToken(user);
                return AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
            }
        }
        return null;
    }
}
