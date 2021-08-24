package ru.iteco.fmh.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.iteco.fmh.model.task.claim.ClaimComment;

import java.util.List;

@Repository
public interface ClaimCommentRepository extends JpaRepository<ClaimComment, Integer> {
    List<ClaimComment> findAllByClaim_Id(int id);
    ClaimComment findClaimCommentById (int id);
}
