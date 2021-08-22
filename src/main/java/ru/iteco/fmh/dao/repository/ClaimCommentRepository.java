package ru.iteco.fmh.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iteco.fmh.model.task.claim.ClaimComment;

public interface ClaimCommentRepository extends JpaRepository<ClaimComment, Integer> {
}
