package ru.iteco.fmh.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.iteco.fmh.model.admission.Admission;

import java.util.List;
import java.util.Set;

@Repository
public interface AdmissionRepository extends JpaRepository<Admission, Integer> {

    List<Admission> findAllByPatient_IdAndDeletedIsFalse(Integer patientId);

    Set<Admission> findAdmissionsByPatientId(Integer id);
}
