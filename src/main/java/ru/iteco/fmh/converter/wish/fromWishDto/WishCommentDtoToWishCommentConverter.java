package ru.iteco.fmh.converter.wish.fromWishDto;

import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import ru.iteco.fmh.converter.user.fromUserDto.IUserDtoToUserConverter;
import ru.iteco.fmh.dto.wish.WishCommentDto;
import ru.iteco.fmh.model.task.wish.Wish;
import ru.iteco.fmh.model.task.wish.WishComment;
import ru.iteco.fmh.model.user.User;

public class WishCommentDtoToWishCommentConverter implements Converter<WishCommentDto, WishComment> {
    private final IUserDtoToUserConverter userDtoToUserConverter;
    private final WishDtoToWishConverter wishDtoToWishConverter;

    public WishCommentDtoToWishCommentConverter(IUserDtoToUserConverter userDtoToUserConverter, WishDtoToWishConverter wishDtoToWishConverter) {
        this.userDtoToUserConverter = userDtoToUserConverter;
        this.wishDtoToWishConverter = wishDtoToWishConverter;
    }

    @Override
    public WishComment convert(WishCommentDto wishCommentDto) {
        WishComment wishComment = new WishComment();
        BeanUtils.copyProperties(wishCommentDto, wishComment);
        Wish wish = wishDtoToWishConverter.convert(wishCommentDto.getWish());
        User creator = userDtoToUserConverter.convert(wishCommentDto.getCreator());
        wishComment.setWish(wish);
        wishComment.setCreator(creator);
        return wishComment;
    }
}
