package ru.iteco.fmh.dao.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.TestUtils;
import ru.iteco.fmh.model.user.Profile;
import ru.iteco.fmh.model.user.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    UserRepository repository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Test
    public void testWriteSuccess() {
        Profile profile = Profile.builder()
                .firstName(TestUtils.getAlphabeticString())
                .lastName(TestUtils.getAlphabeticString())
                .middleName(TestUtils.getAlphabeticString())
                .email(TestUtils.getAlphabeticString())
                .dateOfBirth(LocalDate.now())
                .build();
        User entity = User.builder()
                .login(TestUtils.getAlphabeticString())
                .password(TestUtils.getAlphabeticString())
                .profile(profile)
                .deleted(false)
                .build();

        entity = repository.save(entity);
        assertNotNull(entity.getId());
        assertNotNull(entity.getProfile().getId());
        repository.delete(entity);
    }
}