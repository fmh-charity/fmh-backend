package ru.iteco.fmh.dao.repository;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.model.Note;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
                .description(getAlphabeticString())
                .patient(patient)
                .creator(author)
                .executor(doer)
                .createDate(LocalDateTime.now())
                .comment(getAlphabeticString())
                .build();

        entity = repository.save(entity);

        entity2 = Note.builder()
                .description(getAlphabeticString())
                .patient(patient)
                .creator(author)
                .executor(doer)
                .createDate(LocalDateTime.now())
                .comment(getAlphabeticString())
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
    }

    private Patient getPatient() {
        return Patient.builder()
                .firstName(getAlphabeticString())
                .lastName(getAlphabeticString())
                .middleName(getAlphabeticString())
                .birthDate(LocalDate.now())
                .build();
    }

    private User getUser() {
        return User.builder()
                .firstName(getAlphabeticString())
                .lastName(getAlphabeticString())
                .middleName(getAlphabeticString())
                .build();
    }
}