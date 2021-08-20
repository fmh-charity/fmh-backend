package ru.iteco.fmh.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import ru.iteco.fmh.model.task.wish.Wish;
import ru.iteco.fmh.model.task.StatusE;

import java.util.Collection;
import java.util.List;

@Repository
public interface WishRepository extends JpaRepository<Wish, Integer> {
    List<Wish> findAllByStatusInOrderByPlanExecuteDateAscCreateDateAsc(Collection<StatusE> status);
    List<Wish> findAllByPatient_IdAndDeletedIsFalseAndStatus(Integer patientId, StatusE status);
}
