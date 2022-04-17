package ru.iteco.fmh.dao.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.TestUtils;
import ru.iteco.fmh.model.Patient;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PatientRepositoryTest {

    @Autowired
    PatientRepository repository;
    Patient entity;

    @Test
    public void testWriteSuccess() {
        entity = Patient.builder()
                .firstName(TestUtils.getAlphabeticString())
                .lastName(TestUtils.getAlphabeticString())
                .middleName(TestUtils.getAlphabeticString())
                .birthDate(Instant.now())
                .build();
        entity = repository.save(entity);
        assertNotNull(entity.getId());
        repository.delete(entity);
    }
}