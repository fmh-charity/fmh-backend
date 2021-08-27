package ru.iteco.fmh.converter.wish.fromWish;

import lombok.NonNull;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import ru.iteco.fmh.dto.wish.WishShortInfoDto;
import ru.iteco.fmh.model.task.wish.Wish;

public class WishToWishShortDtoConverter implements Converter<Wish, WishShortInfoDto> {

    @Override
    public WishShortInfoDto convert(@NonNull Wish wish) {
        WishShortInfoDto dto = new WishShortInfoDto();
        BeanUtils.copyProperties(wish, dto);

        dto.setShortPatientName(wish.getPatient().getShortPatientName());
        dto.setShortExecutorName(wish.getExecutor().getShortUserName());

        return dto;
    }
}
