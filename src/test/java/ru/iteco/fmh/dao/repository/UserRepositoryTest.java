package ru.iteco.fmh.dao.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.TestUtils;
import ru.iteco.fmh.model.user.User;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    UserRepository repository;

    @Test
    public void testWriteSuccess() {
        User entity = User.builder()
                .firstName(TestUtils.getAlphabeticString())
                .lastName(TestUtils.getAlphabeticString())
                .middleName(TestUtils.getAlphabeticString())
                .build();
        entity = repository.save(entity);
        assertNotNull(entity.getId());
        repository.delete(entity);
    }
}