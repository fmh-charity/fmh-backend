package ru.iteco.fmh.dao.repository;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.model.task.StatusE;
import ru.iteco.fmh.model.task.wish.Wish;
import ru.iteco.fmh.model.task.wish.WishComment;
import ru.iteco.fmh.model.user.User;

import java.time.LocalDateTime;

import static org.junit.Assert.assertNotNull;
import static ru.iteco.fmh.TestUtils.*;
import static ru.iteco.fmh.model.task.StatusE.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WishCommentRepositoryTest {
    @Autowired
    WishCommentRepository sut;

    static WishComment testEntity1;
    static WishComment testEntity2;

    @Test
    public void saveToRepositorytestShouldPassSuccess() {
        Wish wish = getWish(OPEN);
        wish.setId(1);
        User user = getUser();
        user.setId(1);

        testEntity1 = WishComment.builder()
                .description(getAlphabeticStringR())
                .createDate(LocalDateTime.now())
                .creator(user)
                .wish(wish)
                .build();

        testEntity1 = sut.save(testEntity1);

        testEntity2 = WishComment.builder()
                .description(getAlphabeticStringR())
                .createDate(LocalDateTime.now())
                .creator(user)
                .wish(wish)
                .build();

        testEntity2 = sut.save(testEntity2);

        Assertions.assertAll(
                () -> assertNotNull(testEntity1.getId()),
                () -> assertNotNull(testEntity2.getId())
        );

        // after
        sut.delete(testEntity1);
        sut.delete(testEntity2);
    }
}
