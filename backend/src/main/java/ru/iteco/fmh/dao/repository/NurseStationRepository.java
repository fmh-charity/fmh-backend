package ru.iteco.fmh.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.iteco.fmh.model.NurseStation;

import java.util.List;
import java.util.Optional;

@Repository
public interface NurseStationRepository extends JpaRepository<NurseStation, Integer> {

    List<NurseStation> findAllByDeletedIsFalse();

    Optional<NurseStation> findByIdAndDeletedIsFalse(int id);

}
