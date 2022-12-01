package ru.iteco.fmh.converter.wish;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dto.wish.WishCommentInfoDto;
import ru.iteco.fmh.model.task.wish.WishComment;

@Component
@RequiredArgsConstructor
public class WishCommentToWishCommentInfoDtoConverter implements Converter<WishComment, WishCommentInfoDto> {

    @Override
    public WishCommentInfoDto convert(WishComment wishComment) {
        // UserInfoDtoFio назначаем отдельно
        return new WishCommentInfoDto(
                wishComment.getCreateDate().toEpochMilli(),
                wishComment.getDescription(),
                wishComment.getId());
    }
}
