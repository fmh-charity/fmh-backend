package ru.iteco.fmh.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.iteco.fmh.model.NurseStation;

@Repository
public interface NurseStationRepository extends JpaRepository<NurseStation, Integer> {
}
