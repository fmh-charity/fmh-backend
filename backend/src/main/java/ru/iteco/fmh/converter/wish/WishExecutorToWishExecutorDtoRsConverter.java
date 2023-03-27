package ru.iteco.fmh.converter.wish;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dto.wish.WishExecutorDtoRs;
import ru.iteco.fmh.model.wish.WishExecutor;

@Component
@RequiredArgsConstructor
public class WishExecutorToWishExecutorDtoRsConverter implements Converter<WishExecutor, WishExecutorDtoRs> {


    @Override
    public WishExecutorDtoRs convert(WishExecutor source) {
        Long finishDate = source.getFinishDate() == null ? null : source.getFinishDate().toEpochMilli();

        return WishExecutorDtoRs.builder()
                .executorId(source.getExecutor().getId())
                .joinDate(source.getJoinDate().toEpochMilli())
                .finishDate(finishDate)
                .build();
    }
}
