package ru.iteco.fmh.dao.repository;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.TestUtils;
import ru.iteco.fmh.model.user.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    UserRepository repository;
    @Test
    public void testWriteSuccess() {
        User entity = User.builder()
                .firstName(TestUtils.getAlphabeticStringR())
                .lastName(TestUtils.getAlphabeticStringR())
                .middleName(TestUtils.getAlphabeticStringR())
                .build();
        entity = repository.save(entity);
        Assertions.assertNotNull(entity.getId());
        repository.delete(entity);
    }
}