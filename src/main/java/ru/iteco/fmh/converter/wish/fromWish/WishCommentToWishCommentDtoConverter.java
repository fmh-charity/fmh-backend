package ru.iteco.fmh.converter.wish.fromWish;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.converter.user.fromUser.UserToUserDtoConverter;
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
        WishDto wish = wishToWishDtoConverter.convert(wishComment.getWish());
        UserDto creator = userToUserDtoConverter.convert(wishComment.getCreator());
        dto.setWish(wish);
        dto.setCreator(creator);
        return dto;
    }
}
