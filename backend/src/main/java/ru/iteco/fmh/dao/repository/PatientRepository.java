package ru.iteco.fmh.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.admission.AdmissionsStatus;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
    Page<Patient> findAllWithActiveStatus(List<AdmissionsStatus> status, Pageable pageable);

    Page<Patient> findAllWithInactiveStatus(List<AdmissionsStatus> status, Pageable pageable);

    Page<Patient> findAllWithAnyStatus(List<AdmissionsStatus> status, Pageable pageableList);

    Patient findPatientById(Integer id);

//    Patient findByAdmissionsId(Integer id);
}
