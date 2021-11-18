package ru.iteco.fmh.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ru.iteco.fmh.dao.repository.TokenRepository;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dto.user.UserShortInfoDto;
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
    private final ConversionService conversionService;

    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        User user = userRepository.findUserByLogin(loginRequest.getLogin());

        //get token
        String accessJwtToken = jwtProvider.generateAccessJwtToken(user);
        System.out.println(accessJwtToken);
        String refreshJwtToken = jwtProvider.generateRefreshJwtToken(user);

        Token newToken = Token.builder()
                .user(user)
                .refreshToken(refreshJwtToken)
                .createDate(Instant.now())
                .disabled(false)
                .deleted(false)
                .build();

        tokenRepository.save(newToken);

        return new JwtResponse(accessJwtToken, refreshJwtToken);

    }


    public JwtResponse refreshToken(String refreshToken) {
        Token token = tokenRepository
                .findTokenByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("Такого refresh token не существует"));
        if (token.isDisabled()) {
            throw new IllegalArgumentException("This token is disabled");
        }
        token.setDisabled(true);

        User user = userRepository.findUserById(token.getUser().getId());
        String accessJwtToken = jwtProvider.generateAccessJwtToken(user);
        String refreshJwtToken = jwtProvider.generateRefreshJwtToken(user);

        Token newToken = Token.builder()
                .refreshToken(refreshJwtToken)
                .createDate(Instant.now())
                .disabled(false)
                .deleted(false)
                .build();
        tokenRepository.save(newToken);
        return new JwtResponse(accessJwtToken, refreshJwtToken);
    }

    public UserShortInfoDto getAuthorizedUser(String accessToken) {
        int userId = jwtProvider.getUserIdFromJwtToken(accessToken);
        User user = userRepository.findUserById(userId);
        return conversionService.convert(user, UserShortInfoDto.class);
    }

}

