package ru.iteco.fmh.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.iteco.fmh.model.Block;

import java.util.Optional;

@Repository
public interface BlockRepository extends JpaRepository<Block, Integer> {
    Optional<Object> findBlockByNameAndDeletedFalse(String name);
}
