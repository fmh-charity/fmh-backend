package ru.iteco.fmh.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.iteco.fmh.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
    Patient findPatientById(Integer id);

    Patient findByAdmissionsId(Integer id);
}
