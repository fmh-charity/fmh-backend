package ru.iteco.fmh.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.iteco.fmh.model.task.Status;
import ru.iteco.fmh.model.task.wish.Wish;

import java.util.Collection;
import java.util.List;

@Repository
public interface WishRepository extends JpaRepository<Wish, Integer> {

    Page<Wish> findAllByStatusInAndDeletedIsFalse(List<Status> status, Pageable pageable);

    List<Wish> findAllByStatusInAndDeletedIsFalseOrderByPlanExecuteDateAscCreateDateAsc(Collection<Status> status);

    List<Wish> findAllByPatient_IdAndDeletedIsFalseAndStatusInOrderByPlanExecuteDateAscCreateDateAsc(
            Integer patientId, Collection<Status> status
    );

    List<Wish> findAllByPatient_IdAndDeletedIsFalseOrderByPlanExecuteDateAscCreateDateAsc(Integer patientId);

    Wish findWishById(int id);
}
