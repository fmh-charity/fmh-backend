package ru.iteco.fmh.service;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.controller.WishController;
import ru.iteco.fmh.dao.repository.WishRepository;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dto.wish.WishDto;
import ru.iteco.fmh.dto.user.UserDto;
import ru.iteco.fmh.model.task.wish.Wish;
import ru.iteco.fmh.model.user.User;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static ru.iteco.fmh.TestUtils.getWishDto;
import static ru.iteco.fmh.model.task.StatusE.*;


// ТЕСТЫ ЗАВЯЗАНЫ НА ТЕСТОВЫЕ ДАННЫЕ В БД!!
@RunWith(SpringRunner.class)
@SpringBootTest()
public class WishControllerTest {
    @Autowired
    WishController sut;

    @Autowired
    WishRepository wishRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    ConversionServiceFactoryBean factoryBean;

    //    1
    @Test
    public void getAllWishesShouldPassSuccess() {
        List<String> expected = List.of("wish-title1", "wish-title7", "wish-title8", "wish-title6", "wish-title5", "wish-title2");

        List<String> result = sut.getAllWishes().stream()
                .map(WishDto::getTitle).collect(Collectors.toList());

        assertEquals(expected, result);
    }

    //    2
    @Test
    public void creatWishShouldPassSuccess() {
        //given
        WishDto givenWishDto = getWishDto();
        givenWishDto.getPatient().setId(1);
        givenWishDto.getExecutor().setId(1);
        givenWishDto.getCreator().setId(1);

        Integer resultId = sut.createWish(givenWishDto);
        assertNotNull(resultId);

        Wish result = wishRepository.findById(resultId).get();

        assertEquals(IN_PROGRESS, result.getStatus());

        // AFTER - deleting result entity
        wishRepository.deleteById(resultId);
    }


//    @Test
//    public void addCommentShouldPassSuccess() {
//        // given
//        int noteId = 1;
//        String givenComment = "test comment";
//        String expected = "note1-comment, test comment";
//        WishDto result = sut.addComment(noteId, givenComment);
//        assertEquals(expected, result.getComment());
//    }

    @Test
    public void changeStatusShouldPassSuccess() {
        // given
        int noteId1 = 1;
        WishDto resultCancelled = sut.changeStatus(noteId1, CANCELLED);
        assertEquals(CANCELLED, resultCancelled.getStatus());

        // after
        Wish wish = wishRepository.findById(1).get();
        wish.setStatus(OPEN);
        wishRepository.save(wish);
    }

//    @Test
//    public void getAllActiveNotesSort() {
//        List<WishShortInfoDto> wishShortInfoDtoList = sut.getAllWishes();
//        assertEquals(5, wishShortInfoDtoList.size());
//        assertTrue(wishShortInfoDtoList.get(1).getPlanExecuteDate().isBefore
//                (wishShortInfoDtoList.get(2).getPlanExecuteDate()));
//    }


//    @Test
//    public void updateNoteShouldPassSuccess() {
//        ConversionService conversionService = factoryBean.getObject();
//
//        // given
//        int claimId = 3;
//        WishDto given = conversionService.convert(wishRepository.findById(claimId).get(), WishDto.class);
//        String newComment = "new comment";
//
//        assertNotEquals(given.getComment(), newComment);
//
//        given.setComment(newComment);
//
//        WishDto result = sut.updateNote(given);
//
//        assertEquals(given.getComment(), result.getComment());
//    }
}
