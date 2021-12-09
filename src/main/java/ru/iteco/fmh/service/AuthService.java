package ru.iteco.fmh.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iteco.fmh.dao.repository.TokenRepository;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dto.user.UserShortInfoDto;
import ru.iteco.fmh.model.Token;
import ru.iteco.fmh.model.user.User;
import ru.iteco.fmh.security.JwtProvider;
import ru.iteco.fmh.security.JwtResponse;
import ru.iteco.fmh.security.LoginRequest;
import ru.iteco.fmh.security.RefreshTokenRequest;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final ConversionService conversionService;

    @Transactional
    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        if (loginRequest.getLogin() == null) {
            throw new IllegalArgumentException("Логин не может ровняться null");
        }
        User user = userRepository.findUserByLogin(loginRequest.getLogin());
        if (!user.getPassword().equals(loginRequest.getPassword())) {
            throw new IllegalArgumentException("Password is wrong!");
        }
        //get token
        String accessJwtToken = jwtProvider.generateAccessJwtToken(user);
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


    public JwtResponse refreshToken(RefreshTokenRequest refreshToken) {
        System.out.println(refreshToken);
        Token token = tokenRepository.findTokenByRefreshToken(refreshToken.getRefreshToken());
        if (token == null) {
            throw new IllegalArgumentException("Такого refresh token не существует");
        }
        if (token.isDisabled()) {
            throw new IllegalArgumentException("This token is disabled");
        }
        token.setDisabled(true);
        tokenRepository.save(token);


        User user = userRepository.findUserById(token.getUser().getId());
        String accessJwtToken = jwtProvider.generateAccessJwtToken(user);
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

    public UserShortInfoDto getAuthorizedUser(Authentication authentication) {
        return conversionService.convert(userRepository.findUserByLogin(authentication.getName()),
                UserShortInfoDto.class);
    }

}

