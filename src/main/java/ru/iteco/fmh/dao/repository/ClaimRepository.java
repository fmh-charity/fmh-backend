package ru.iteco.fmh.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iteco.fmh.model.Claim;

public interface ClaimRepository extends JpaRepository<Claim, Integer> {
}
