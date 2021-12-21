package ru.iteco.fmh.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.impl.TextCodec;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import ru.iteco.fmh.model.user.User;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

//https://www.baeldung.com/spring-injection-lombok#constructor-injection-with-lombok
@RequiredArgsConstructor
@Component
public class JwtProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
    private static final String key = "Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=";

    //сгенерировать токен JWT (Access Token)
    public String generateAccessJwtToken(User userPrincipal) {
        //генерируем токен(Instant.now().plus(1, ChronoUnit.DAYS)))
        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setId(String.valueOf(userPrincipal.getId()))
                .setExpiration(Timestamp.from(Instant.now().plus(2, ChronoUnit.MINUTES)))
                .signWith(
                        SignatureAlgorithm.HS512,
                        setSigningKey(key)
                )
                .compact();
    }

    //сгенерировать токен JWT (refresh token)(Instant.now().plus(30, ChronoUnit.DAYS)))
    public String generateRefreshJwtToken(User userPrincipal) {
        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setId(String.valueOf(userPrincipal.getId()))
                .setExpiration(Timestamp.from(Instant.now().plus(5, ChronoUnit.MINUTES)))
                .signWith(
                        SignatureAlgorithm.HS512,
                        setSigningKey(key)
                )
                .compact();
    }


    public byte[] setSigningKey(String base64EncodedKeyBytes) {
        Assert.hasText(base64EncodedKeyBytes, "signing key cannot be null or empty.");
        return TextCodec.BASE64.decode(base64EncodedKeyBytes);
    }

    //анализировать имя пользователя из проверенного JWT
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(setSigningKey(key))
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    //получаем ID пользователя из проверенного JWT
    public int getUserIdFromJwtToken(String accessToken) {
        String id = Jwts.parser()
                .setSigningKey(setSigningKey(key))
                .parseClaimsJws(accessToken)
                .getBody()
                .getId();
        return Integer.parseInt(id);
    }


    //проверить токен JWT
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser()
                    .setSigningKey(setSigningKey(key))
                    .parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token -> Message: {}", e);
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token -> Message: {}", e);
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token -> Message: {}", e);
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty -> Message: {}", e);
        }

        return false;
    }
}
