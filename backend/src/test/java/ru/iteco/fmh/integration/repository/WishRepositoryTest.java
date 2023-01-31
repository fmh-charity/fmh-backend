package ru.iteco.fmh.integration.repository;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.TestUtils;
import ru.iteco.fmh.dao.repository.PatientRepository;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dao.repository.WishRepository;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.task.Status;
import ru.iteco.fmh.model.task.wish.Wish;
import ru.iteco.fmh.model.user.User;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WishRepositoryTest {

    @Autowired
    WishRepository wishRepository;
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    UserRepository userRepository;

    static Patient patient;
    static User author;
    static User doer;
    static Wish entity;
    static Wish entity2;

    @Test
    public void testWriteSuccess() {
        patient = getPatient();
        patient = patientRepository.save(patient);
        author = getUser();
        userRepository.save(author);
        doer = getUser();
        userRepository.save(doer);
        entity = Wish.builder()
                .description(TestUtils.getAlphabeticString())
                .patient(patient)
                .creator(author)
                .executor(null)
                .createDate(Instant.now())
                .factExecuteDate(null)
                .planExecuteDate(Instant.now().plus(2, ChronoUnit.DAYS))
                .status(Status.OPEN)

                .build();

        entity = wishRepository.save(entity);
        entity2 = Wish.builder()
                .description(TestUtils.getAlphabeticString())
                .patient(patient)
                .creator(author)
                .executor(doer)
                .createDate(Instant.now().minus(1, ChronoUnit.DAYS))
                .factExecuteDate(Instant.now())
                .planExecuteDate(Instant.now().plus(2, ChronoUnit.DAYS))
                .status(Status.EXECUTED)

                .build();

        entity2 = wishRepository.save(entity2);

        Assertions.assertAll(
                () -> assertNotNull(entity.getId()),
                () -> assertNotNull(entity2.getId())
        );

        wishRepository.delete(entity);
        wishRepository.delete(entity2);
        userRepository.delete(author);
        userRepository.delete(doer);
        patientRepository.delete(patient);
    }

    private Patient getPatient() {
        return Patient.builder()
                .firstName(TestUtils.getAlphabeticString())
                .lastName(TestUtils.getAlphabeticString())
                .middleName(TestUtils.getAlphabeticString())
                .birthDate(LocalDate.now())
                .build();
    }

    private User getUser() {
        return User.builder()
                .firstName(TestUtils.getAlphabeticString())
                .lastName(TestUtils.getAlphabeticString())
                .middleName(TestUtils.getAlphabeticString())
                .build();
    }
}