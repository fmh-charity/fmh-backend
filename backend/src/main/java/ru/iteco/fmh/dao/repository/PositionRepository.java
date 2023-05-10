package ru.iteco.fmh.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iteco.fmh.model.employee.Position;

public interface PositionRepository extends JpaRepository<Position, Integer> {
}
