package ru.iteco.fmh.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iteco.fmh.model.task.wish.WishComment;

import java.util.List;

public interface WishCommentRepository extends JpaRepository<WishComment, Integer> {
    List<WishComment> findAllByWish_Id(Integer wishId);
}
