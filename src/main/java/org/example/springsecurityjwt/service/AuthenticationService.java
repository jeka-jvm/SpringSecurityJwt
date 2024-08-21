package org.example.springsecurityjwt.service;

import lombok.RequiredArgsConstructor;
import org.example.springsecurityjwt.entity.Role;
import org.example.springsecurityjwt.entity.User;
import org.example.springsecurityjwt.model.AuthenticationRequest;
import org.example.springsecurityjwt.model.AuthenticationResponse;
import org.example.springsecurityjwt.model.RegisterRequest;
import org.example.springsecurityjwt.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        User user = User.builder()
                       .firstName(registerRequest.getFirstName())
                       .lastName(registerRequest.getLastName())
                       .email(registerRequest.getEmail())
                       .password(passwordEncoder.encode(registerRequest.getPassword()))
                       .role(Role.USER)
                       .build();

        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                                     .token(jwtToken)
                                     .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        User user = userRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
