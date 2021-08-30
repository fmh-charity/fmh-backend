package ru.iteco.fmh.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.controller.WishesController;
import ru.iteco.fmh.dao.repository.WishCommentRepository;
import ru.iteco.fmh.dao.repository.WishRepository;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dto.wish.WishCommentDto;
import ru.iteco.fmh.dto.wish.WishDto;
import ru.iteco.fmh.model.task.wish.Wish;
import ru.iteco.fmh.model.task.wish.WishComment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static ru.iteco.fmh.TestUtils.getWishCommentDto;
import static ru.iteco.fmh.TestUtils.getWishDto;
import static ru.iteco.fmh.model.task.StatusE.*;


// ТЕСТЫ ЗАВЯЗАНЫ НА ТЕСТОВЫЕ ДАННЫЕ В БД!!
@RunWith(SpringRunner.class)
@SpringBootTest()
public class WishesControllerTest {
    @Autowired
    WishesController sut;

    @Autowired
    WishRepository wishRepository;

    @Autowired
    WishCommentRepository wishCommentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ConversionService conversionService;

    @Test
    public void getOpenInProgressWishesShouldPassSuccess() {
        List<String> expected = List.of("wish-title1", "wish-title7", "wish-title8", "wish-title6", "wish-title5", "wish-title2");

        List<String> result = sut.getAllOpenInProgressWishes().stream()
                .map(WishDto::getTitle).collect(Collectors.toList());

        assertEquals(expected, result);
    }

    @Test
    public void getAllWishesShouldPassSuccess() {
        List<String> expected = List.of("wish-title1", "wish-title7", "wish-title8", "wish-title6",
                "wish-title5", "wish-title4", "wish-title3", "wish-title2");

        List<String> result = sut.getAllWishes().stream()
                .map(WishDto::getTitle).collect(Collectors.toList());

        assertEquals(expected, result);
    }

    @Test
    public void createInProgressWishShouldPassSuccess() {
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

    @Test
    public void createOpenWishShouldPassSuccess() {
        //given
        WishDto givenWishDto = getWishDto();
        givenWishDto.getPatient().setId(1);
        givenWishDto.getCreator().setId(1);
        givenWishDto.setExecutor(null);

        Integer resultId = sut.createWish(givenWishDto);
        assertNotNull(resultId);

        Wish result = wishRepository.findById(resultId).get();

        assertEquals(OPEN, result.getStatus());

        // AFTER - deleting result entity
        wishRepository.deleteById(resultId);
    }

    @Test
    public void getWishShouldPassSuccess() {
        int wishId = 1;
        WishDto expected = conversionService.convert(wishRepository.findById(wishId).get(), WishDto.class);
        WishDto result = sut.getWish(wishId);

        assertEquals(expected, result);
    }


    @Test
    public void updateWishShouldPassSuccess() {
        // given
        int wishId = 1;
        WishDto given = conversionService.convert(wishRepository.findById(wishId).get(), WishDto.class);
        String initialDescription = given.getDescription();
        given.setDescription("new description");

        WishDto result = sut.updateWish(given);

        assertEquals(given.getDescription(), result.getDescription());

        // after
        given.setDescription(initialDescription);
        wishRepository.save(conversionService.convert(given, Wish.class));
    }

    @Test
    public void changeStatusOpenToCancelledShouldPassSuccess() {
        // given
        int wishOpenId = 1;

        WishDto result = sut.changeStatus(wishOpenId, CANCELLED);

        assertEquals(CANCELLED, result.getStatus());

        // after
        Wish wish = wishRepository.findById(wishOpenId).get();
        wish.setStatus(OPEN);
        wishRepository.save(wish);
    }

    @Test
    public void changeStatusOpenToInProgressShouldPassSuccess() {
        // given
        int wishOpenId = 1;

        WishDto result = sut.changeStatus(wishOpenId, IN_PROGRESS);

        assertEquals(IN_PROGRESS, result.getStatus());

        // after
        Wish wish = wishRepository.findById(wishOpenId).get();
        wish.setStatus(OPEN);
        wishRepository.save(wish);
    }

    @Test
    public void changeStatusInProgressToExecutedShouldPassSuccess() {
        // given
        int wishInProgressId = 2;

        WishDto result = sut.changeStatus(wishInProgressId, EXECUTED);

        assertEquals(EXECUTED, result.getStatus());
        assertEquals(LocalDateTime.now().withNano(0), result.getFactExecuteDate());

        // after
        Wish wish = wishRepository.findById(wishInProgressId).get();
        wish.setStatus(IN_PROGRESS);
        wishRepository.save(wish);
    }

    @Test
    public void changeStatusInProgressToOpenShouldPassSuccess() {
        // given
        int wishInProgressId = 2;

        WishDto result = sut.changeStatus(wishInProgressId, OPEN);

        assertEquals(OPEN, result.getStatus());
        assertNull(result.getExecutor());

        // after
        Wish wish = wishRepository.findById(wishInProgressId).get();
        wish.setStatus(IN_PROGRESS);
        wishRepository.save(wish);
    }

    // тесты на смену неверных статусов имеются в WishServiceTest

    @Test
    public void getWishCommentShouldPassSuccess() {
        // given
        int commentId = 1;

        WishCommentDto expected = conversionService.convert(wishCommentRepository.findById(commentId).get(), WishCommentDto.class);

        WishCommentDto result = sut.getWishComment(commentId);

        assertEquals(expected, result);
    }

    @Test
    public void getAllWishCommentsShouldPassSuccess() {
        // given
        int wishId = 1;
        List<WishCommentDto> expected = wishCommentRepository.findAllByWish_Id(wishId).stream()
                .map(i -> conversionService.convert(i, WishCommentDto.class))
                .collect(Collectors.toList());

        List<WishCommentDto> result = sut.getAllWishComments(wishId);

        assertEquals(expected, result);
    }

    @Test
    public void createWishCommentShouldPassSuccess() {
        // given
        WishCommentDto givenWishCommentDto = getWishCommentDto(OPEN);
        int wishId = 1;
        int creatorId = 1;
        givenWishCommentDto.getWish().setId(wishId);
        givenWishCommentDto.getCreator().setId(creatorId);

        Integer resultId = sut.createWishComment(wishId, givenWishCommentDto);

        assertNotNull(resultId);

        // AFTER - deleting result entity
        wishCommentRepository.deleteById(resultId);
    }

    @Test
    public void updateWishCommentShouldPassSuccess() {
        // given
        int wishCommentId = 1;
        WishCommentDto givenWishCommentDto = conversionService
                .convert(wishCommentRepository.findById(wishCommentId).get(), WishCommentDto.class);
        String initialDescription = givenWishCommentDto.getDescription();
        givenWishCommentDto.setDescription("new description");

        WishCommentDto result = sut.updateWishComment(givenWishCommentDto);

        assertEquals(givenWishCommentDto, result);

        // after
        givenWishCommentDto.setDescription(initialDescription);
        wishCommentRepository.save(conversionService.convert(givenWishCommentDto, WishComment.class));
    }
}