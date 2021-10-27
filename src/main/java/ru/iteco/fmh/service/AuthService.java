package ru.iteco.fmh.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import ru.iteco.fmh.dao.repository.TokenRepository;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.model.Token;
import ru.iteco.fmh.security.JwtProvider;
import ru.iteco.fmh.security.JwtResponse;
import ru.iteco.fmh.security.LoginRequest;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        //convert user

        //get token
        String accessJwtToken = jwtProvider.generateAccessJwtToken(userDetails);
        String refreshJwtToken = jwtProvider.generateRefreshJwtToken(userDetails);

        Token newToken = Token.builder()
                .user(userRepository.findUserById(userDetails.getId()))
                .refreshToken(refreshJwtToken)
                .createDate(Instant.now())
                .deleted(false)
                .build();

        tokenRepository.save(newToken);

        return new JwtResponse(accessJwtToken, refreshJwtToken);

    }

}

