package ru.iteco.fmh.dao.repository;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.model.admission.Admission;
import ru.iteco.fmh.model.Patient;

import java.time.LocalDate;

import static ru.iteco.fmh.TestUtils.getAlphabeticString;

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
                .birthDate(LocalDate.now())
                .build();

        patient = patientRepository.save(patient);

        entity = Admission.builder()
                .factDateIn(LocalDate.now())
                .patient(patient)
                .build();

        entity = repository.save(entity);

        Assertions.assertNotNull(entity.getId());

        repository.delete(entity);
    }
}