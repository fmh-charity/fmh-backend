package ru.iteco.fmh.dao.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.PatientStatus;

import java.util.Collection;
import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
    Patient findPatientById(Integer id);

    List<Patient> findAllByStatusIn(Collection<PatientStatus> status);

    @Query("SELECT o FROM Patient o WHERE lower(o.profile.firstName) LIKE LOWER(concat('%', :searchValue, '%'))"
            + " OR lower(o.profile.middleName) LIKE LOWER(concat('%', :searchValue, '%'))"
            + " OR lower(o.profile.lastName) LIKE LOWER(concat('%', :searchValue, '%'))"
            + " OR lower(o.room.name) LIKE LOWER(concat('%', :searchValue, '%'))"
            + " OR lower(o.status) LIKE LOWER(concat('%', :searchValue, '%'))"
    )
    List<Patient> findAllByParameters(PageRequest pageRequest, @Param("searchValue") String searchValue);
}
