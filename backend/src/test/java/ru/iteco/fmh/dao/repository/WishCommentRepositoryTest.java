package ru.iteco.fmh.dao.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.TestUtils;
import ru.iteco.fmh.model.wish.Wish;
import ru.iteco.fmh.model.wish.WishComment;
import ru.iteco.fmh.model.user.User;

import java.time.Instant;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertAll;
import static ru.iteco.fmh.TestUtils.*;
import static ru.iteco.fmh.model.wish.Status.OPEN;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WishCommentRepositoryTest {
    @Autowired
    WishCommentRepository sut;

    static WishComment testEntity1;
    static WishComment testEntity2;

    @Test
    public void saveToRepositoryTestShouldPassSuccess() {
        Wish wish = getWish(OPEN);
        wish.setId(1);
        User user = getUser(getProfile());
        user.setId(1);

        testEntity1 = WishComment.builder()
                .description(TestUtils.getAlphabeticString())
                .createDate(Instant.now())
                .creator(user)
                .wish(wish)
                .build();

        testEntity1 = sut.save(testEntity1);

        testEntity2 = WishComment.builder()
                .description(TestUtils.getAlphabeticString())
                .createDate(Instant.now())
                .creator(user)
                .wish(wish)
                .build();

        testEntity2 = sut.save(testEntity2);

        assertAll(
                () -> assertNotNull(testEntity1.getId()),
                () -> assertNotNull(testEntity2.getId())
        );

        // after
        sut.delete(testEntity1);
        sut.delete(testEntity2);
    }
}
