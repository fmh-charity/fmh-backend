package ru.iteco.fmh.converter.wish;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.converter.user.UserToUserDtoIdFioConverter;
import ru.iteco.fmh.dto.user.UserDtoIdFio;
import ru.iteco.fmh.dto.wish.WishExecutorDtoRs;
import ru.iteco.fmh.model.wish.WishExecutor;

@Component
@RequiredArgsConstructor
public class WishExecutorToWishExecutorDtoRsConverter implements Converter<WishExecutor, WishExecutorDtoRs> {
    private final UserToUserDtoIdFioConverter userToUserDtoIdFioConverter;

    @Override
    public WishExecutorDtoRs convert(WishExecutor source) {
        UserDtoIdFio wishExecutorFio = userToUserDtoIdFioConverter.convert(source.getExecutor());
        Long finishDate = source.getFinishDate() == null ? null : source.getFinishDate().toEpochMilli();

        return WishExecutorDtoRs.builder()
                .id(source.getId())
                .executor(wishExecutorFio)
                .joinDate(source.getJoinDate().toEpochMilli())
                .finishDate(finishDate)
                .build();
    }
}
