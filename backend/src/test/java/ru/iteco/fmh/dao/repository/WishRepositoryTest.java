package ru.iteco.fmh.dao.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.TestUtils;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.wish.Status;
import ru.iteco.fmh.model.wish.Wish;
import ru.iteco.fmh.model.user.User;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;

import static org.junit.Assert.assertNotNull;
import static ru.iteco.fmh.TestUtils.getNumeric;


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
        author =  userRepository.save(TestUtils.getUser(TestUtils.getProfile()));
        doer = userRepository.save(TestUtils.getUser(TestUtils.getProfile()));
        entity = Wish.builder()
                .description(TestUtils.getAlphabeticString())
                .patient(patient)
                .creator(author)
                .createDate(Instant.now())
                .factExecuteDate(null)
                .planExecuteDate(Instant.now().plus(2, ChronoUnit.DAYS))
                .executors(Collections.emptySet())
                .status(Status.OPEN)
                .build();

        entity = wishRepository.save(entity);
        entity2 = Wish.builder()
                .description(TestUtils.getAlphabeticString())
                .patient(patient)
                .creator(author)
                .createDate(Instant.now().minus(1, ChronoUnit.DAYS))
                .factExecuteDate(Instant.now())
                .planExecuteDate(Instant.now().plus(2, ChronoUnit.DAYS))
                .executors(Collections.emptySet())
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
                .id(Integer.valueOf(getNumeric(2)))
                .deleted(false)
                .profile(TestUtils.getProfile())
                .build();
    }
}