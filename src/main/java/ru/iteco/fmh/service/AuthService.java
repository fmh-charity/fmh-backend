package ru.iteco.fmh.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.iteco.fmh.dao.repository.TokenRepository;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.model.Token;
import ru.iteco.fmh.model.user.User;
import ru.iteco.fmh.security.JwtProvider;
import ru.iteco.fmh.security.JwtResponse;
import ru.iteco.fmh.security.LoginRequest;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        User user = userRepository.findUserByLogin(loginRequest.getLogin());

        //get token
        String accessJwtToken = jwtProvider.generateAccessJwtToken(user);
        String refreshJwtToken = jwtProvider.generateRefreshJwtToken(user);

        Token newToken = Token.builder()
                .refreshToken(refreshJwtToken)
                .createDate(Instant.now())
                .deleted(false)
                .build();

        tokenRepository.save(newToken);

        return new JwtResponse(accessJwtToken, refreshJwtToken);

    }

}

