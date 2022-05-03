package ru.iteco.fmh.converter;

import org.junit.jupiter.api.Test;
import ru.iteco.fmh.converter.wish.WishToWishDtoConverter;
import ru.iteco.fmh.dto.wish.WishDto;
import ru.iteco.fmh.model.task.wish.Wish;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static ru.iteco.fmh.TestUtils.getWish;
import static ru.iteco.fmh.model.task.Status.IN_PROGRESS;
import static ru.iteco.fmh.model.task.Status.OPEN;


class WishToWishDtoConverterTest {

    WishToWishDtoConverter convertor = new WishToWishDtoConverter();

    @Test
    void convertWishForOpen() {
        Wish wish = getWish(OPEN);
        wish.setExecutor(null);
        WishDto dto = convertor.convert(wish);
        assertAll(
                () -> assertEquals(wish.getId(), dto.getId()),
                () -> assertEquals(wish.getPatient().getId(), dto.getPatientId()),
                () -> assertEquals(wish.getDescription(), dto.getDescription()),
                () -> assertEquals(wish.getPlanExecuteDate().toEpochMilli(), dto.getPlanExecuteDate()),
                () -> assertEquals(dto.getFactExecuteDate(), wish.getFactExecuteDate().toEpochMilli()),
                () -> assertEquals(wish.getStatus(), dto.getStatus()),
                () -> assertEquals(dto.getCreatorId(), wish.getCreator().getId()),
                () -> assertEquals(dto.getExecutorId(), wish.getExecutor()),
                () -> assertNull(dto.getExecutorId()),
                () -> assertNull(wish.getExecutor())
        );
    }

    @Test
    void convertWishForInProgress() {
        Wish wish = getWish(IN_PROGRESS);
        WishDto dto = convertor.convert(wish);
        assertAll(
                () -> assertEquals(wish.getId(), dto.getId()),
                () -> assertEquals(wish.getPatient().getId(), dto.getPatientId()),
                () -> assertEquals(wish.getDescription(), dto.getDescription()),
                () -> assertEquals(wish.getPlanExecuteDate().toEpochMilli(), dto.getPlanExecuteDate()),
                () -> assertEquals(dto.getFactExecuteDate(), wish.getFactExecuteDate().toEpochMilli()),
                () -> assertEquals(wish.getStatus(), dto.getStatus()),
                () -> assertEquals(dto.getCreatorId(), wish.getCreator().getId()),
                () -> assertEquals(dto.getExecutorId(), wish.getExecutor().getId()),
                () -> assertNotNull(dto.getExecutorId()),
                () -> assertNotNull(wish.getExecutor())
        );
    }

}




