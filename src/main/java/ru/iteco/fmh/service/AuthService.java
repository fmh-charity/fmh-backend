package ru.iteco.fmh.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.iteco.fmh.security.JwtProvider;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public String authenticateUser(String login, String password) {
        //convert user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //get token
        String jwt = jwtProvider.generateJwtToken(authentication);
        return null;

    }
}
