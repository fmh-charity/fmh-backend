package ru.iteco.fmh.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.dao.repository.WishRepository;
import ru.iteco.fmh.dto.wish.WishDto;
import ru.iteco.fmh.model.task.wish.Wish;
import ru.iteco.fmh.service.wish.WishService;

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

    @Autowired
    ConversionServiceFactoryBean factoryBean;


    // 1
    @Test
    public void getOpenInProgressWishesShouldPassSuccess() {
        // given
        ConversionService conversionService = factoryBean.getObject();
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
        ConversionService conversionService = factoryBean.getObject();
        List<Wish> wishList = List.of(getWish(OPEN), getWish(CANCELLED));
        List<WishDto> expected = wishList.stream().map(wish -> conversionService.convert(wish, WishDto.class))
                .collect(Collectors.toList());

        when(wishRepository.findAllByDeletedIsFalseOrderByPlanExecuteDateAscCreateDateAsc())
                .thenReturn(wishList);
        List<WishDto> result = sut.getAllWishes();

        assertEquals(expected, result);
    }

    // 2
    @Test
    public void createWishShouldPassSuccess() {
        // given
        Wish wish = getWish(null);
        wish.setId(1);
        WishDto dto = factoryBean.getObject().convert(wish, WishDto.class);

        when(wishRepository.save(any())).thenReturn(wish);
        Integer resultId = sut.createWish(dto);

        assertEquals(1, resultId);
        assertEquals(IN_PROGRESS, dto.getStatus());
    }

    // 3
    @Test
    public void getWishShouldPassSuccess() {
        ConversionService conversionService = factoryBean.getObject();

        // given
        Wish wish = getWish(OPEN);
        int wishId = 1;

        when(wishRepository.findById(any())).thenReturn(Optional.of(wish));
        WishDto expected = conversionService.convert(wish, WishDto.class);
        WishDto result = sut.getWish(wishId);

        assertEquals(expected, result);
    }

    // 4
    @Test
    public void updateWishShouldPassSuccess() {
        // given
        Wish wish = getWish(OPEN);
        WishDto givenDto = factoryBean.getObject().convert(wish, WishDto.class);

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
    public void changeStatusShouldPassSuccess() {
        // given
        Wish activeWish = getWish(OPEN);
        Wish cancelledWish = getWish(CANCELLED);

        when(wishRepository.findById(any())).thenReturn(Optional.of(activeWish));
        when(wishRepository.save(any())).thenReturn(cancelledWish);
        WishDto result = sut.changeStatus(any(), CANCELLED);
        assertEquals(CANCELLED, result.getStatus());
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
        ConversionService conversionService = factoryBean.getObject();
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
        ConversionService conversionService = factoryBean.getObject();
        Integer patientId = 1;

        List<Wish> openInProgressWishList = List.of(getWish(OPEN), getWish(IN_PROGRESS));
        List<WishDto> expected = openInProgressWishList.stream().map(wish -> conversionService.convert(wish, WishDto.class))
                .collect(Collectors.toList());

        when(wishRepository.findAllByPatient_IdAndDeletedIsFalseAndStatusInOrderByPlanExecuteDateAscCreateDateAsc(patientId, List.of(OPEN, IN_PROGRESS)))
                .thenReturn(openInProgressWishList);
        List<WishDto> result = sut.getPatientOpenInProgressWishes(patientId);

        assertEquals(expected, result);
    }


//    @Test
//    public void addCommentShouldPassSuccess() {
//        // given
//        Wish wish = getNote(OPEN);
//        Wish resultWish = getNote(OPEN);
//        String newComment = "test comment";
//        String expected = "first comment".concat(", ").concat(newComment);
//        resultWish.setComment(expected);
//        when(wishRepository.findById(any())).thenReturn(Optional.of(wish));
//        when(wishRepository.save(any())).thenReturn(resultWish);
//        WishDto result = sut.addComment(any(), newComment);
//        assertEquals(expected, result.getComment());
//    }
}
