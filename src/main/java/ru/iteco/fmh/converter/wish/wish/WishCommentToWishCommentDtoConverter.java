package ru.iteco.fmh.converter.wish.wish;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.converter.user.user.UserToUserDtoConverter;
import ru.iteco.fmh.dto.user.UserDto;
import ru.iteco.fmh.dto.wish.WishCommentDto;
import ru.iteco.fmh.dto.wish.WishDto;
import ru.iteco.fmh.model.task.wish.WishComment;

@Component
@RequiredArgsConstructor
public class WishCommentToWishCommentDtoConverter implements Converter<WishComment, WishCommentDto> {

    private final UserToUserDtoConverter userToUserDtoConverter;
    private final WishToWishDtoConverter wishToWishDtoConverter;

    @Override
    public WishCommentDto convert(@NonNull WishComment wishComment) {
        WishCommentDto dto = new WishCommentDto();
        BeanUtils.copyProperties(wishComment, dto);

        WishDto wish = wishComment.getWish() != null ? wishToWishDtoConverter.convert(wishComment.getWish()) : null;
        UserDto creator = wishComment.getCreator() != null ? userToUserDtoConverter.convert(wishComment.getCreator()) : null;
        dto.setWish(wish);
        dto.setCreator(creator);
        return dto;
    }
}
