package ru.iteco.fmh.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iteco.fmh.model.Block;

public interface BlockRepository extends JpaRepository<Block, Integer> {
}
