package com.al_baraka_digital.Baraka.service;

import com.al_baraka_digital.Baraka.enums.Role;
import com.al_baraka_digital.Baraka.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User createUser(String email, String password, String firstname, String lastname, Role role);
    List<User> getAllUsers();
    User getUserById(UUID id);
    void deleteUser(UUID id);
}
