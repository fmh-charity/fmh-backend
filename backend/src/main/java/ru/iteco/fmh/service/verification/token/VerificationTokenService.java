package ru.iteco.fmh.service.verification.token;

import ru.iteco.fmh.model.user.User;

/**
 * сервис для работы с кодом подтверждения
 */
public interface VerificationTokenService {
    /**
     * Подтверждение email пользователя
     *
     * @param token - код подтверждения
     */
    void verifyEmail(String token);

    /**
     * Генерация токена и отправка ссылки
     * для подтверждения email текущего пользователя
     */
    void generateAndSendVerificationEmail();

    /**
     * Генерация токена и отправка ссылки
     * для подтверждения email пользователя
     */
    void generateAndSendVerificationEmail(User user);
}
