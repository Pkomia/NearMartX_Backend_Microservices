package com._Rbrothers.user_service.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com._Rbrothers.user_service.dto.LoginRequest;
import com._Rbrothers.user_service.dto.LoginResponse;
import com._Rbrothers.user_service.dto.RegisterRequest;
import com._Rbrothers.user_service.dto.UserResponse;
import com._Rbrothers.user_service.entity.Role;
import com._Rbrothers.user_service.entity.User;
import com._Rbrothers.user_service.repository.RoleRepository;
import com._Rbrothers.user_service.repository.UserRepository;
import com._Rbrothers.user_service.security.JwtService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    public void register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        Role customerRole = roleRepository
                .findByRoleName("CUSTOMER")
                .orElseThrow(() -> new RuntimeException("Default role not found"));

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.getRoles().add(customerRole);

        userRepository.save(user);
    } 

    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(user);

        return LoginResponse.builder()
                .token(token)
                .build();
    }

    public UserResponse getUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Set<String> roleNames = user.getRoles()
                .stream()
                .map(Role::getRoleName)
                .collect(Collectors.toSet());

        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .roles(roleNames)
                .build();
    }

}
