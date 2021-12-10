package ru.iteco.fmh.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.iteco.fmh.model.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {
    Token findTokenByRefreshToken(String refreshToken);
}
