package com._Rbrothers.user_service.service;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com._Rbrothers.user_service.dto.LoginRequest;
import com._Rbrothers.user_service.dto.LoginResponse;
import com._Rbrothers.user_service.dto.RegisterRequest;
import com._Rbrothers.user_service.dto.UserResponse;
import com._Rbrothers.user_service.entity.RefreshToken;
import com._Rbrothers.user_service.entity.Role;
import com._Rbrothers.user_service.entity.User;
import com._Rbrothers.user_service.exception.EmailAlreadyExistsException;
import com._Rbrothers.user_service.exception.InvalidCredentialsException;
import com._Rbrothers.user_service.repository.RefreshTokenRepository;
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
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already registered");
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
                .orElseThrow(() -> new InvalidCredentialsException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }
        
        // delete previous refresh token
        refreshTokenRepository.deleteByUser(user);

        String token = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        RefreshToken refreshTokenEntity = RefreshToken.builder()
                                          .token(refreshToken)
                                          .expiryDate(Instant.now().plusSeconds(7*24*60*60))
                                          .user(user)
                                          .build();
        
        if (refreshTokenEntity != null) {
            refreshTokenRepository.save(refreshTokenEntity);
        }

        return LoginResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional
    public LoginResponse refreshToken(String requestToken) {

        RefreshToken refreshToken = refreshTokenRepository.findByToken(requestToken)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException("Refresh token expired");
        }

        User user = refreshToken.getUser();

        String newAccessToken = jwtService.generateToken(user);

        return LoginResponse.builder()
                .token(newAccessToken)
                .refreshToken(requestToken)
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
