package ru.iteco.fmh.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iteco.fmh.model.NurseStation;

public interface NurseStationRepository extends JpaRepository<NurseStation, Integer> {
}
