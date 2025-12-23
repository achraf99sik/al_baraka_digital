package com.al_baraka_digital.Baraka.service.impl;

import com.al_baraka_digital.Baraka.enums.Role;
import com.al_baraka_digital.Baraka.exception.AlreadyExistsException;
import com.al_baraka_digital.Baraka.exception.ResourceNotFoundException;
import com.al_baraka_digital.Baraka.model.User;
import com.al_baraka_digital.Baraka.repository.UserRepository;
import com.al_baraka_digital.Baraka.service.AccountService;
import com.al_baraka_digital.Baraka.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccountService accountService;

    @Override
    @Transactional
    public User createUser(String email, String password, String firstname, String lastname, Role role) {
         if (userRepository.findByEmail(email).isPresent()) {
            throw new AlreadyExistsException("Email already taken");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setFullName(firstname+ ' ' + lastname);
        user.setRole(role);
        user.setActive(true);
        
        User savedUser = userRepository.save(user);
        
        if (role == Role.CLIENT) {
            accountService.createAccount(savedUser);
        }

        return savedUser;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    @Override
    public User getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    @Transactional
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }
}
