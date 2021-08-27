package ru.iteco.fmh.converter.wish.fromWishDto;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.converter.user.fromUserDto.UserDtoToUserConverter;
import ru.iteco.fmh.dto.wish.WishCommentDto;
import ru.iteco.fmh.model.task.wish.Wish;
import ru.iteco.fmh.model.task.wish.WishComment;
import ru.iteco.fmh.model.user.User;

@Component
@RequiredArgsConstructor
public class WishCommentDtoToWishCommentConverter implements Converter<WishCommentDto, WishComment> {

    private final UserDtoToUserConverter userDtoToUserConverter;
    private final WishDtoToWishConverter wishDtoToWishConverter;

    @Override
    public WishComment convert(@NonNull WishCommentDto wishCommentDto) {
        WishComment wishComment = new WishComment();
        BeanUtils.copyProperties(wishCommentDto, wishComment);
        Wish wish = wishDtoToWishConverter.convert(wishCommentDto.getWish());
        User creator = userDtoToUserConverter.convert(wishCommentDto.getCreator());
        wishComment.setWish(wish);
        wishComment.setCreator(creator);
        return wishComment;
    }
}
