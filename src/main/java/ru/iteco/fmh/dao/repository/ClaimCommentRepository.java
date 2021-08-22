package ru.iteco.fmh.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iteco.fmh.model.task.claim.ClaimComment;

import java.util.List;

public interface ClaimCommentRepository extends JpaRepository<ClaimComment, Integer> {
    List<ClaimComment> findAllByClaim_Id(Integer id);
}
