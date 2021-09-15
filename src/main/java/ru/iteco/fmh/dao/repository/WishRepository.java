package ru.iteco.fmh.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.iteco.fmh.model.task.StatusE;
import ru.iteco.fmh.model.task.wish.Wish;

import java.util.Collection;
import java.util.List;

@Repository
public interface WishRepository extends JpaRepository<Wish, Integer> {

    List<Wish> findAllByDeletedIsFalseOrderByPlanExecuteDateAscCreateDateAsc();

    List<Wish> findAllByStatusInAndDeletedIsFalseOrderByPlanExecuteDateAscCreateDateAsc(Collection<StatusE> status);

    List<Wish> findAllByPatient_IdAndDeletedIsFalseAndStatusInOrderByPlanExecuteDateAscCreateDateAsc(
            Integer patientId, Collection<StatusE> status
    );

    List<Wish> findAllByPatient_IdAndDeletedIsFalseOrderByPlanExecuteDateAscCreateDateAsc(Integer patientId);

    Wish findWishById(int id);
}
