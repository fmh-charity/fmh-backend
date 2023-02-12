package ru.iteco.fmh.service.verification.token;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dao.repository.VerificationTokenRepository;
import ru.iteco.fmh.exceptions.InvalidTokenException;
import ru.iteco.fmh.exceptions.NotFoundException;
import ru.iteco.fmh.model.VerificationToken;
import ru.iteco.fmh.model.user.User;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VerificationTokenServiceImpl implements VerificationTokenService {

    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(VerificationTokenService.class);

    public void verifyEmail(String token) {
        final String tokenNotFoundMessage = "Токен для подтверждения email не найден!";
        final String tokenHasExpireMessage = "Истёк срок действия токена. Повторите процесс подтверждения email!";
        final String emailConfirmMessage = "Email пользователя %s подтвержден.";

        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);

        if (verificationToken.isEmpty()) {
            LOGGER.error(tokenNotFoundMessage);
            throw new NotFoundException(tokenNotFoundMessage);
        }

        if (verificationToken.get().getExpiryDate().compareTo(Instant.now()) < 0) {
            LOGGER.error(tokenHasExpireMessage);
            throw new InvalidTokenException(tokenHasExpireMessage);

        }

        User userOnConfirmation = verificationToken.get().getUser();
        userOnConfirmation.setEmailConfirmed(true);
        userRepository.save(userOnConfirmation);

        LOGGER.info(String.format(emailConfirmMessage, userOnConfirmation.getEmail()));
    }
}
