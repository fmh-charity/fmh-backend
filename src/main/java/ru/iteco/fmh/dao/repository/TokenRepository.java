package ru.iteco.fmh.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iteco.fmh.model.Token;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {


    Optional<Token> findTokenByRefreshToken(String refreshToken);
}
