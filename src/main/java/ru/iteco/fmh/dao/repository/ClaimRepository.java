package ru.iteco.fmh.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iteco.fmh.model.Claim;
import ru.iteco.fmh.model.Note;
import ru.iteco.fmh.model.StatusE;

import java.util.List;

public interface ClaimRepository extends JpaRepository<Claim, Integer> {

    List<Claim> findAllByStatusOrderByPlanExecuteDate(StatusE status);

}
