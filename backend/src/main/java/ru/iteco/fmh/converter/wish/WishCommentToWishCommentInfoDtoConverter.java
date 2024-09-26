package ru.iteco.fmh.converter.wish;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.converter.user.UserToUserDtoIdFioConverter;
import ru.iteco.fmh.dto.user.UserDtoIdFio;
import ru.iteco.fmh.dto.wish.WishCommentInfoDto;
import ru.iteco.fmh.model.wish.WishComment;

@Component
@RequiredArgsConstructor
public class WishCommentToWishCommentInfoDtoConverter implements Converter<WishComment, WishCommentInfoDto> {
    private final UserToUserDtoIdFioConverter userToUserDtoIdFioConverter;

    @Override
    public WishCommentInfoDto convert(WishComment wishComment) {
        UserDtoIdFio userDtoIdFio = userToUserDtoIdFioConverter.convert(wishComment.getCreator());
        return new WishCommentInfoDto(userDtoIdFio,
                wishComment.getCreateDate().toEpochMilli(),
                wishComment.getDescription(),
                wishComment.getId());
    }
}
