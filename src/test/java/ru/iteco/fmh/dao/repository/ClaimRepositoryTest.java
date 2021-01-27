package ru.iteco.fmh.dao.repository;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.model.Claim;
import ru.iteco.fmh.model.User;

import java.time.LocalDate;

import static org.junit.Assert.assertNotNull;
import static ru.iteco.fmh.TestUtils.getAlphabeticString;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClaimRepositoryTest {

    @Autowired
    ClaimRepository repository;
    @Autowired
    UserRepository userRepository;

    User author;
    User doer;
    Claim entity;
    Claim entity2;


    @Test
    public void testWriteSuccess() {
        author = getUser();
        userRepository.save(author);
        doer = getUser();
        userRepository.save(doer);
        entity = Claim.builder()
                .text(getAlphabeticString())
                .creator(author)
                .executor(doer)
                .dateCreate(LocalDate.now())
                .comment(getAlphabeticString())
                .build();

        entity = repository.save(entity);

        entity2 = Claim.builder()
                .text(getAlphabeticString())
                .creator(author)
                .executor(doer)
                .dateCreate(LocalDate.now())
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

    private User getUser() {
        return User.builder()
                .firstName(getAlphabeticString())
                .lastName(getAlphabeticString())
                .middleName(getAlphabeticString())
                .build();
    }
}