package ru.iteco.fmh.dao.repository;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.model.Employee;
import ru.iteco.fmh.model.Note;
import ru.iteco.fmh.model.Patient;

import java.time.LocalDate;

import static org.junit.Assert.assertNotNull;
import static ru.iteco.fmh.TestUtils.getAlphabeticString;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NoteRepositoryTest {

    @Autowired
    NoteRepository repository;
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    EmployeeRepository employeeRepository;

    static Patient patient;
    static Employee author;
    static Employee doer;
    static Note entity;
    static Note entity2;


    @Test
    public void testWriteSuccess() {
        patient = getPatient();
        patient = patientRepository.save(patient);
        author = getEmployer();
        employeeRepository.save(author);
        doer = getEmployer();
        employeeRepository.save(doer);
        entity = Note.builder()
                .text(getAlphabeticString())
                .patient(patient)
                .author(author)
                .doer(doer)
                .dateCreate(LocalDate.now())
                .comment(getAlphabeticString())
                .build();

        entity = repository.save(entity);

        entity2 = Note.builder()
                .text(getAlphabeticString())
                .patient(patient)
                .author(author)
                .doer(doer)
                .dateCreate(LocalDate.now())
                .comment(getAlphabeticString())
                .build();

        entity2 = repository.save(entity2);

        Assertions.assertAll(
                () -> assertNotNull(entity.getId()),
                () -> assertNotNull(entity2.getId())
        );

        repository.delete(entity);
        repository.delete(entity2);
        employeeRepository.delete(author);
        employeeRepository.delete(doer);
    }

    private Patient getPatient() {
        return Patient.builder()
                .firstName(getAlphabeticString())
                .lastName(getAlphabeticString())
                .middleName(getAlphabeticString())
                .birthday(LocalDate.now())
                .build();
    }

    private Employee getEmployer() {
        return Employee.builder()
                .firstName(getAlphabeticString())
                .lastName(getAlphabeticString())
                .middleName(getAlphabeticString())
                .build();
    }
}