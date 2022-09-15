package ru.iteco.fmh.dao.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.iteco.fmh.model.task.Status;
import ru.iteco.fmh.model.task.claim.Claim;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

@Repository
public interface ClaimRepository extends PagingAndSortingRepository<Claim, Integer> {

    List<Claim> findAllByStatusInAndDeletedIsFalseOrderByPlanExecuteDateAscCreateDateAsc(Collection<Status> status);

    List<Claim> findAllByStatus(Status status, Pageable pageable);

    List<Claim> findAllByDeletedIsFalseOrderByPlanExecuteDateAscCreateDateAsc();

    Claim findClaimById(int id);
}
