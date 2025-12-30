package com.al_baraka_digital.Baraka.service;

import com.al_baraka_digital.Baraka.dto.AuthenticationRequest;
import com.al_baraka_digital.Baraka.dto.AuthenticationResponse;
import com.al_baraka_digital.Baraka.dto.RegisterRequest;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    AuthenticationResponse refreshToken(HttpServletRequest request);
}
