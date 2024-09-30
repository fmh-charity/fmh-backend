package ru.iteco.fmh.dao.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static ru.iteco.fmh.TestUtils.*;
import ru.iteco.fmh.model.Patient;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class PatientRepositoryTest {

    @Autowired
    PatientRepository repository;
    Patient entity;

    @Test
    public void testWriteSuccess() {
        entity = Patient.builder()
                .id(Integer.valueOf(getNumeric(2)))
                .deleted(false)
                .profile(getProfile())
                .build();
        entity = repository.save(entity);
        assertNotNull(entity.getId());
        repository.delete(entity);
    }
}