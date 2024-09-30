package ru.iteco.fmh.dao.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.TestUtils;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.user.Profile;
import ru.iteco.fmh.model.wish.Status;
import ru.iteco.fmh.model.wish.Wish;
import ru.iteco.fmh.model.user.User;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;

import static org.junit.Assert.assertNotNull;
import static ru.iteco.fmh.TestUtils.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class WishRepositoryTest {

    @Autowired
    WishRepository wishRepository;

    @Autowired
    private TestEntityManager entityManager;


    @Test
    public void testWriteSuccess() {
        final User author =  entityManager.merge(getUser(TestUtils.getProfile()));
        final Patient patient = entityManager.merge(TestUtils.getPatient());

         Wish entity = Wish.builder()
                .description(getAlphabeticString())
                .patient(patient)
                .creator(author)
                .createDate(Instant.now())
                .factExecuteDate(null)
                .planExecuteDate(Instant.now().plus(2, ChronoUnit.DAYS))
                .executors(Collections.emptySet())
                .status(Status.OPEN)
                .build();

        final Wish entityAfterSave = wishRepository.save(entity);



        Assertions.assertNotNull(entityAfterSave.getId());



    }
}