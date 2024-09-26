package ru.iteco.fmh.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iteco.fmh.model.wish.WishExecutor;

import java.util.Set;

public interface WishExecutorsRepository extends JpaRepository<WishExecutor, Integer> {
    Set<WishExecutor> findAllByWishId(Integer wishId);
}
