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
import ru.iteco.fmh.model.task.StatusE;
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

    @Autowired
    ConversionServiceFactoryBean factoryBean;


    // 1
    @Test
    public void getAllWishesShouldPassSuccess() {
        // given
        ConversionService conversionService = factoryBean.getObject();
        List<Wish> wishList = List.of(getWish(OPEN), getWish(IN_PROGRESS));
        List<WishDto> expected = wishList.stream().map(wish->conversionService.convert(wish,WishDto.class))
                .collect(Collectors.toList());

        when(wishRepository.findAllByStatusInOrderByPlanExecuteDateAscCreateDateAsc(any()))
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

//3
    @Test
    public void getWishShouldPassSuccess() {
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

//    @Test
//    public void changeStatusWhenNonActiveNoteShouldThrowNoteException() {
//        // given
//        Wish executedWish = getWish(EXECUTED);
//        when(wishRepository.findById(any())).thenReturn(Optional.of(executedWish));
//        assertThrows(IllegalArgumentException.class,
//                () -> sut.changeStatus(any(), CANCELLED));
//    }
//

//
//    @Test
//    public void updateNoteShouldPassSuccess() {
//        ConversionService conversionService = factoryBean.getObject();
//
//        // given
//        Wish wish = getWish(OPEN);
//        WishDto given = conversionService.convert(wish, WishDto.class);
//
//        when(wishRepository.save(any())).thenReturn(wish);
//
//        WishDto result = sut.updateWish(given);
//
//        assertAll(
//                () -> assertEquals(given.getId(), result.getId()),
//                () -> assertEquals(given.getPatient(), result.getPatient()),
//                () -> assertEquals(given.getDescription(), result.getDescription()),
//                () -> assertEquals(given.getPlanExecuteDate(), result.getPlanExecuteDate()),
//                () -> assertEquals(given.getFactExecuteDate(), result.getFactExecuteDate()),
//                () -> assertEquals(given.getCreateDate(), result.getCreateDate()),
//                () -> assertEquals(given.getStatus(), result.getStatus()),
//                () -> assertEquals(given.getExecutor(), result.getExecutor()),
//                () -> assertEquals(given.getCreator(), result.getCreator())
//        );
//    }
}
