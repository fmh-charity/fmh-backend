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
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

//https://www.baeldung.com/spring-injection-lombok#constructor-injection-with-lombok
@RequiredArgsConstructor
@Component
public class JwtProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
    private static final String key = "Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=";

    //сгенерировать токен JWT
    public String generateJwtToken(Authentication authentication) {
        //get our user like userPrincipal
        UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();
        //генерируем токен
        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
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

    //анализировать имя пользователя из из проверенного JWT
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(setSigningKey(key))
                .parseClaimsJws(token)
                .getBody().getSubject();
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
