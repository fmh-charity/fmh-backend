package ru.iteco.fmh.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.iteco.fmh.converter.wish.fromWish.WishToWishShortDtoConverter;
import ru.iteco.fmh.dto.wish.WishShortInfoDto;
import ru.iteco.fmh.model.task.StatusE;
import ru.iteco.fmh.model.task.wish.Wish;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.iteco.fmh.TestUtils.getWish;
import static ru.iteco.fmh.model.task.StatusE.*;

class WishToWishShortDtoConverterTest {
    WishToWishShortDtoConverter convertor = new WishToWishShortDtoConverter();
    @Test
    void convert() {
        Wish wish = getWish(OPEN);
        WishShortInfoDto shortInfoDto = convertor.convert(wish);
        Assertions.assertAll(
                () -> assertEquals(wish.getId(), shortInfoDto.getId()),
                () -> assertEquals(wish.getPlanExecuteDate(), shortInfoDto.getPlanExecuteDate()),
                () -> assertEquals(wish.getFactExecuteDate(), shortInfoDto.getFactExecuteDate()),
                () -> assertEquals(wish.getPatient().getShortPatientName(), shortInfoDto.getShortPatientName()),
                () -> assertEquals(wish.getExecutor().getShortUserName(), shortInfoDto.getShortExecutorName()),
                () ->  assertEquals(wish.getStatus(),shortInfoDto.getStatus()),
                () ->  assertEquals(wish.getDescription(),shortInfoDto.getDescription())
        );
    }
}