package ru.iteco.fmh.service.verification.token;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dao.repository.VerificationTokenRepository;
import ru.iteco.fmh.exceptions.InvalidTokenException;
import ru.iteco.fmh.exceptions.NotFoundException;
import ru.iteco.fmh.exceptions.UnavailableOperationException;
import ru.iteco.fmh.model.VerificationToken;
import ru.iteco.fmh.model.user.User;
import ru.iteco.fmh.service.mail.notifier.Notifier;
import ru.iteco.fmh.service.mail.notifier.SendEmailNotifierContext;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class VerificationTokenServiceImpl implements VerificationTokenService {

    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;
    private final Notifier<SendEmailNotifierContext> sendEmailNotifier;

    @Value("${vhospice-app.email-verification-life-in-hours}")
    private Integer tokenLifeTime;
    @Value("${spring.mail.username}")
    private String emailFromAddress;
    @Value("${static-host.host}")
    private String staticHost;

    public void verifyEmail(String tokenId) {
        VerificationToken verificationToken = verificationTokenRepository.findById(tokenId)
                .orElseThrow(() -> new NotFoundException("Токен для подтверждения email не найден!"));

        if (verificationToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            throw new InvalidTokenException("Истёк срок действия токена. Повторите процесс подтверждения email!");
        }

        User userOnConfirmation = verificationToken.getUser();
        userOnConfirmation.setEmailConfirmed(true);
        userRepository.save(userOnConfirmation);

        log.info("Email пользователя {} подтвержден.", userOnConfirmation.getEmail());
    }

    public void generateAndSendVerificationEmail() {
        int principalId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        User currentLoggedInUser = userRepository.findUserById(principalId);

        if (currentLoggedInUser.isEmailConfirmed()) {
            throw new UnavailableOperationException("Данный Email уже подтвержден");
        }

        String verificationToken = createVerificationToken(currentLoggedInUser);
        String verificationUri = "https://" + staticHost + "/users/verify/email?token=" + verificationToken;
        String content = """
            Уважаемый пользователь!
            Для подтверждения вашей электронной почты в системе перейдите, пожалуйста, по ссылке: %s"
            Если это письмо пришло вам по ошибке, просто проигнорируйте его.""".formatted(verificationUri);

        SendEmailNotifierContext sendEmailNotifierContext = SendEmailNotifierContext.builder()
                .toAddress(currentLoggedInUser.getEmail())
                .fromAddress(emailFromAddress)
                .senderName("Vhospice")
                .subject("Подтверждение регистрации vhospice.org")
                .content(content)
                .build();

        sendEmailNotifier.send(sendEmailNotifierContext);
        log.info("Ссылка для подтверждения Email пользователя {} успешно отправлена", currentLoggedInUser.getLogin());
    }

    private String createVerificationToken(User currentLoggedInUser) {
        Instant tokenExpiredTime = Instant.now().plus(tokenLifeTime, ChronoUnit.HOURS);
        String tokenId = UUID.randomUUID().toString();
        VerificationToken verificationToken = VerificationToken.builder()
                .id(tokenId)
                .user(currentLoggedInUser)
                .expiryDate(tokenExpiredTime)
                .build();
        verificationTokenRepository.save(verificationToken);
        log.info("Токен для подтверждения Email пользователя {} создан", currentLoggedInUser.getLogin());
        return tokenId;
    }
}
