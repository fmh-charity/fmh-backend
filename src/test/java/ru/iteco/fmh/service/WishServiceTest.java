package ru.iteco.fmh.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.dao.repository.WishCommentRepository;
import ru.iteco.fmh.dao.repository.WishRepository;
import ru.iteco.fmh.dto.wish.WishCommentDto;
import ru.iteco.fmh.dto.wish.WishDto;
import ru.iteco.fmh.model.task.wish.Wish;
import ru.iteco.fmh.model.task.wish.WishComment;
import ru.iteco.fmh.service.wish.WishService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ru.iteco.fmh.TestUtils.*;
import static ru.iteco.fmh.model.task.StatusE.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WishServiceTest {
    @Autowired
    WishService sut;

    @MockBean
    WishRepository wishRepository;

    @MockBean
    WishCommentRepository wishCommentRepository;

    @Autowired
    ConversionService conversionService;

    @Test
    public void getOpenInProgressWishesShouldPassSuccess() {
        // given
        List<Wish> wishList = List.of(getWish(OPEN), getWish(IN_PROGRESS));
        List<WishDto> expected = wishList.stream().map(wish -> conversionService.convert(wish, WishDto.class))
                .collect(Collectors.toList());

        when(wishRepository.findAllByStatusInAndDeletedIsFalseOrderByPlanExecuteDateAscCreateDateAsc(any()))
                .thenReturn(wishList);
        List<WishDto> result = sut.getOpenInProgressWishes();

        assertEquals(expected, result);
    }

    @Test
    public void getAllWishesShouldPassSuccess() {
        // given
        List<Wish> wishList = List.of(getWish(OPEN), getWish(CANCELLED));
        List<WishDto> expected = wishList.stream().map(wish -> conversionService.convert(wish, WishDto.class))
                .collect(Collectors.toList());

        when(wishRepository.findAllByDeletedIsFalseOrderByPlanExecuteDateAscCreateDateAsc())
                .thenReturn(wishList);
        List<WishDto> result = sut.getAllWishes();

        assertEquals(expected, result);
    }

    @Test
    public void createWishShouldPassSuccess() {
        // given
        Wish wish = getWish(null);
        wish.setId(1);
        WishDto dto = conversionService.convert(wish, WishDto.class);

        when(wishRepository.save(any())).thenReturn(wish);
        Integer resultId = sut.createWish(dto);

        assertEquals(1, resultId);
        assertEquals(IN_PROGRESS, dto.getStatus());
    }

    @Test
    public void getWishShouldPassSuccess() {
        // given
        Wish wish = getWish(OPEN);
        int wishId = 1;

        when(wishRepository.findById(any())).thenReturn(Optional.of(wish));
        WishDto expected = conversionService.convert(wish, WishDto.class);
        WishDto result = sut.getWish(wishId);

        assertEquals(expected, result);
    }

    @Test
    public void updateWishShouldPassSuccess() {
        // given
        Wish wish = getWish(OPEN);
        WishDto givenDto = conversionService.convert(wish, WishDto.class);

        when(wishRepository.save(any())).thenReturn(wish);

        WishDto resultDto = sut.updateWish(givenDto);

        assertAll(
                () -> assertEquals(givenDto.getId(), resultDto.getId()),
                () -> assertEquals(givenDto.getPatient(), resultDto.getPatient()),
                () -> assertEquals(givenDto.getDescription(), resultDto.getDescription()),
                () -> assertEquals(givenDto.getPlanExecuteDate(), resultDto.getPlanExecuteDate()),
                () -> assertEquals(givenDto.getFactExecuteDate(), resultDto.getFactExecuteDate()),
                () -> assertEquals(givenDto.getCreateDate(), resultDto.getCreateDate()),
                () -> assertEquals(givenDto.getExecutor(), resultDto.getExecutor()),
                () -> assertEquals(givenDto.getCreator(), resultDto.getCreator())
        );

        assertEquals(IN_PROGRESS, givenDto.getStatus());
    }

    @Test
    public void changeStatusOpenToCancelledShouldPassSuccess() {
        // given
        int wishId = 1;
        Wish givenWish = getWish(OPEN);

        when(wishRepository.findById(any())).thenReturn(Optional.of(givenWish));
        when(wishRepository.save(any())).thenReturn(givenWish);

        WishDto result = sut.changeStatus(wishId, CANCELLED);
        assertEquals(CANCELLED, result.getStatus());
    }

    @Test
    public void changeStatusOpenToInProgressShouldPassSuccess() {
        // given
        int wishId = 1;
        Wish givenWish = getWish(OPEN);

        when(wishRepository.findById(any())).thenReturn(Optional.of(givenWish));
        when(wishRepository.save(any())).thenReturn(givenWish);

        WishDto result = sut.changeStatus(wishId, IN_PROGRESS);
        assertEquals(IN_PROGRESS, result.getStatus());
    }

    @Test
    public void changeStatusInProgressToExecutedShouldPassSuccess() {
        // given
        int wishId = 1;
        Wish givenWish = getWish(IN_PROGRESS);
        givenWish.setFactExecuteDate(null);

        when(wishRepository.findById(any())).thenReturn(Optional.of(givenWish));
        when(wishRepository.save(any())).thenReturn(givenWish);

        WishDto result = sut.changeStatus(wishId, EXECUTED);
        assertEquals(EXECUTED, result.getStatus());
        assertEquals(LocalDateTime.now().withNano(0), result.getFactExecuteDate());
    }

    @Test
    public void changeStatusInProgressToCancelledShouldThrowException() {
        // given
        Integer wishId = 1;
        Wish inProgressWish = getWish(IN_PROGRESS);

        when(wishRepository.findById(any())).thenReturn(Optional.of(inProgressWish));

        assertThrows(IllegalArgumentException.class, () -> sut.changeStatus(wishId, CANCELLED));
    }

    @Test
    public void changeStatusOpenToExecutedShouldThrowException() {
        // given
        Integer wishId = 1;
        Wish openWish = getWish(OPEN);

        when(wishRepository.findById(any())).thenReturn(Optional.of(openWish));

        assertThrows(IllegalArgumentException.class, () -> sut.changeStatus(wishId, EXECUTED));
    }

    @Test
    public void changeStatusCancelledToAnyShouldThrowException() {
        // given
        Integer wishId = 1;
        Wish cancelledWish = getWish(CANCELLED);

        when(wishRepository.findById(any())).thenReturn(Optional.of(cancelledWish));
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> sut.changeStatus(wishId, EXECUTED)),
                () -> assertThrows(IllegalArgumentException.class, () -> sut.changeStatus(wishId, OPEN)),
                () -> assertThrows(IllegalArgumentException.class, () -> sut.changeStatus(wishId, IN_PROGRESS))
        );
    }

    @Test
    public void changeStatusExecutedToAnyShouldThrowException() {
        // given
        Integer wishId = 1;
        Wish executedWish = getWish(EXECUTED);

        when(wishRepository.findById(any())).thenReturn(Optional.of(executedWish));
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> sut.changeStatus(wishId, CANCELLED)),
                () -> assertThrows(IllegalArgumentException.class, () -> sut.changeStatus(wishId, OPEN)),
                () -> assertThrows(IllegalArgumentException.class, () -> sut.changeStatus(wishId, IN_PROGRESS))
        );
    }

    @Test
    public void getPatientAllWishesShouldPassSuccess() {
        // given
        Integer patientId = 1;
        List<Wish> allWishList = List.of(getWish(OPEN), getWish(EXECUTED), getWish(CANCELLED), getWish(IN_PROGRESS));
        List<WishDto> expected = allWishList.stream().map(wish -> conversionService.convert(wish, WishDto.class))
                .collect(Collectors.toList());

        when(wishRepository.findAllByPatient_IdAndDeletedIsFalseOrderByPlanExecuteDateAscCreateDateAsc(patientId))
                .thenReturn(allWishList);
        List<WishDto> result = sut.getPatientAllWishes(patientId);

        assertEquals(expected, result);
    }

    @Test
    public void getPatientOpenInProgressWishes() {
        // given
        Integer patientId = 1;

        List<Wish> openInProgressWishList = List.of(getWish(OPEN), getWish(IN_PROGRESS));
        List<WishDto> expected = openInProgressWishList.stream().map(wish -> conversionService.convert(wish, WishDto.class))
                .collect(Collectors.toList());

        when(wishRepository.findAllByPatient_IdAndDeletedIsFalseAndStatusInOrderByPlanExecuteDateAscCreateDateAsc(patientId, List.of(OPEN, IN_PROGRESS)))
                .thenReturn(openInProgressWishList);
        List<WishDto> result = sut.getPatientOpenInProgressWishes(patientId);

        assertEquals(expected, result);
    }


    @Test
    public void getWishCommentShouldPassSuccess() {
        // given
        WishComment wishComment = getWishComment(OPEN);
        int wishCommentId = 1;

        when(wishCommentRepository.findById(any())).thenReturn(Optional.of(wishComment));
        WishCommentDto expected = conversionService.convert(wishComment, WishCommentDto.class);
        WishCommentDto result = sut.getWishComment(wishCommentId);

        assertEquals(expected, result);
    }

    @Test
    public void getAllWishCommentsShouldPassSuccess() {
        // given
        List<WishComment> wishCommentList = List.of(getWishComment(OPEN), getWishComment(IN_PROGRESS));
        List<WishCommentDto> expected = wishCommentList.stream()
                .map(wishComment -> conversionService.convert(wishComment, WishCommentDto.class))
                .collect(Collectors.toList());
        int wishId = 1;

        when(wishCommentRepository.findAllByWish_Id(any())).thenReturn(wishCommentList);
        List<WishCommentDto> result = sut.getAllWishComments(wishId);

        assertEquals(expected, result);
    }

    @Test
    public void createWishCommentShouldPassSuccess() {
        // given
        Wish wish = getWish(OPEN);
        int wishId = 1;
        WishComment wishComment = getWishComment(OPEN);
        int commentId = 1;
        wishComment.setId(commentId);
        WishCommentDto wishCommentDto = conversionService.convert(wishComment, WishCommentDto.class);

        when(wishCommentRepository.save(any())).thenReturn(wishComment);
        when(wishRepository.findById(any())).thenReturn(Optional.of(wish));
        Integer resultId = sut.createWishComment(wishId, wishCommentDto);

        assertEquals(commentId, resultId);
    }

    @Test
    public void updateWishCommentShouldPassSuccess() {
        // given
        WishComment wishComment = getWishComment(OPEN);
        WishCommentDto givenDto = conversionService.convert(wishComment, WishCommentDto.class);

        when(wishCommentRepository.save(any())).thenReturn(wishComment);

        WishCommentDto resultDto = sut.updateWishComment(givenDto);

        assertEquals(givenDto, resultDto);
    }
}
