package ru.iteco.fmh.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iteco.fmh.model.task.claim.Claim;
import ru.iteco.fmh.model.task.StatusE;

import java.util.Collection;
import java.util.List;

public interface ClaimRepository extends JpaRepository<Claim, Integer> {

    List<Claim> findAllByStatusInOrderByPlanExecuteDateAscCreateDateAsc(Collection<StatusE> status);



}
