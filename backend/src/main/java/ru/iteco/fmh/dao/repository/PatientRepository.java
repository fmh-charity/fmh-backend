package ru.iteco.fmh.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.PatientStatus;

import java.util.Collection;
import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
    Patient findPatientById(Integer id);

    List<Patient> findAllByStatusIn(Collection<PatientStatus> status);
}
