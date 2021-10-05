package ru.iteco.fmh.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iteco.fmh.model.Token;

public interface TokenRepository extends JpaRepository<Token, Integer> {
}
