package com.al_baraka_digital.Baraka.service;

import com.al_baraka_digital.Baraka.model.User;

public interface JwtService {

    String generateAccessToken(User user);

    String generateRefreshToken(User user);
}