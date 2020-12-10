package ru.iteco.fmh.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iteco.fmh.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
