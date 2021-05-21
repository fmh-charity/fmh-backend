package ru.iteco.fmh.dao.repository;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.TestUtils;
import ru.iteco.fmh.model.Note;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.StatusE;
import ru.iteco.fmh.model.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NoteRepositoryTest {

    @Autowired
    NoteRepository repository;
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    UserRepository userRepository;

    static Patient patient;
    static User author;
    static User doer;
    static Note entity;
    static Note entity2;


    @Test
    public void testWriteSuccess() {
        patient = getPatient();
        patient = patientRepository.save(patient);
        author = getUser();
        userRepository.save(author);
        doer = getUser();
        userRepository.save(doer);
        entity = Note.builder()
                .description(TestUtils.getAlphabeticStringR())
                .patient(patient)
                .creator(author)
                .executor(doer)
                .createDate(LocalDateTime.now())
                .factExecuteDate(null)
                .planExecuteDate(LocalDateTime.now().plusDays(2))
                .status(StatusE.active)
                .comment(TestUtils.getAlphabeticStringR())
                .build();

        entity = repository.save(entity);

        entity2 = Note.builder()
                .description(TestUtils.getAlphabeticStringR())
                .patient(patient)
                .creator(author)
                .executor(doer)
                .createDate(LocalDateTime.now().minusDays(1))
                .factExecuteDate(LocalDateTime.now())
                .planExecuteDate(LocalDateTime.now().plusDays(2))
                .status(StatusE.executed)
                .comment(TestUtils.getAlphabeticStringR())
                .build();

        entity2 = repository.save(entity2);

        Assertions.assertAll(
                () -> assertNotNull(entity.getId()),
                () -> assertNotNull(entity2.getId())
        );

        repository.delete(entity);
        repository.delete(entity2);
        userRepository.delete(author);
        userRepository.delete(doer);
        patientRepository.delete(patient);
    }

    private Patient getPatient() {
        return Patient.builder()
                .firstName(TestUtils.getAlphabeticStringR())
                .lastName(TestUtils.getAlphabeticStringR())
                .middleName(TestUtils.getAlphabeticStringR())
                .birthDate(LocalDate.now())
                .build();
    }

    private User getUser() {
        return User.builder()
                .firstName(TestUtils.getAlphabeticStringR())
                .lastName(TestUtils.getAlphabeticStringR())
                .middleName(TestUtils.getAlphabeticStringR())
                .build();
    }
}