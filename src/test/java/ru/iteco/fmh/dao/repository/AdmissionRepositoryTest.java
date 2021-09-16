package ru.iteco.fmh.dao.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.admission.Admission;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AdmissionRepositoryTest {

    @Autowired
    AdmissionRepository repository;
    @Autowired
    PatientRepository patientRepository;
    Patient patient;
    Admission entity;

    @Test
    public void testWriteSuccess() {
        patient = Patient.builder()
                .firstName("first")
                .middleName("first")
                .lastName("first")
                .birthDate(Instant.now())
                .build();

        patient = patientRepository.save(patient);
        entity = Admission.builder()
                .planDateIn(Instant.now())
                .factDateIn(Instant.now())
                .patient(patient)
                .build();

        entity = repository.save(entity);
        assertNotNull(entity.getId());
        repository.delete(entity);
        patientRepository.delete(patient);
    }
}