package ru.iteco.fmh.dao.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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



@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class WishCommentRepositoryTest {
    @Autowired
    WishCommentRepository sut;




    @Test
    public void saveToRepositoryTestShouldPassSuccess() {
        Wish wish = getWish(OPEN);
        wish.setId(1);
        User user = getUser(getProfile());
        user.setId(1);



        WishComment testEntity1 = WishComment.builder()
                .description(TestUtils.getAlphabeticString())
                .createDate(Instant.now())
                .creator(user)
                .wish(wish)
                .build();

        final WishComment testEntity1AfterSave = sut.save(testEntity1);



        assertAll(
                () -> assertNotNull(testEntity1AfterSave.getId())

        );


    }
}
