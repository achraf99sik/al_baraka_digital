package com.al_baraka_digital.Baraka.service;

import com.al_baraka_digital.Baraka.model.User;

import java.util.UUID;

public interface JwtService {

    String generateAccessToken(User user);

    String generateRefreshToken(User user);

    boolean isRefreshTokenValid(String token);

    UUID extractUserId(String token);
}