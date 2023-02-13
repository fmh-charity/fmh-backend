package ru.iteco.fmh.service.verification.token;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dao.repository.VerificationTokenRepository;
import ru.iteco.fmh.exceptions.InvalidTokenException;
import ru.iteco.fmh.exceptions.NotFoundException;
import ru.iteco.fmh.model.VerificationToken;
import ru.iteco.fmh.model.user.User;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
public class VerificationTokenServiceImpl implements VerificationTokenService {

    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;

    public void verifyEmail(String token) {
        final String tokenNotFoundMessage = "Токен для подтверждения email не найден!";
        final String tokenHasExpireMessage = "Истёк срок действия токена. Повторите процесс подтверждения email!";
        final String emailConfirmMessage = "Email пользователя %s подтвержден.";

        VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new NotFoundException(tokenNotFoundMessage));

        if (verificationToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            throw new InvalidTokenException(tokenHasExpireMessage);
        }

        User userOnConfirmation = verificationToken.getUser();
        userOnConfirmation.setEmailConfirmed(true);
        userRepository.save(userOnConfirmation);

        log.info(String.format(emailConfirmMessage, userOnConfirmation.getEmail()));
    }
}
