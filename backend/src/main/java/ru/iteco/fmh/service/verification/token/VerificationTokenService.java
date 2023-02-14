package ru.iteco.fmh.service.verification.token;

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
}
