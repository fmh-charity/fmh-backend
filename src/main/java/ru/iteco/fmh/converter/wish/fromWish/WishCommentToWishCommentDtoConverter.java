package ru.iteco.fmh.converter.wish.fromWish;

import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import ru.iteco.fmh.converter.user.fromUser.IUserToUserDtoConverter;
import ru.iteco.fmh.dto.user.UserDto;
import ru.iteco.fmh.dto.wish.WishCommentDto;
import ru.iteco.fmh.dto.wish.WishDto;
import ru.iteco.fmh.model.task.wish.WishComment;

public class WishCommentToWishCommentDtoConverter implements Converter<WishComment, WishCommentDto> {
   private final IUserToUserDtoConverter userToUserDtoConverter;
   private final WishToWishDtoConverter wishToWishDtoConverter;

    public WishCommentToWishCommentDtoConverter(IUserToUserDtoConverter userToUserDtoConverter, WishToWishDtoConverter wishToWishDtoConverter) {
        this.userToUserDtoConverter = userToUserDtoConverter;
        this.wishToWishDtoConverter = wishToWishDtoConverter;
    }

    @Override
    public WishCommentDto convert(WishComment wishComment) {
        WishCommentDto dto = new WishCommentDto();
        BeanUtils.copyProperties(wishComment, dto);
        WishDto wish = wishToWishDtoConverter.convert(wishComment.getWish());
        UserDto creator = userToUserDtoConverter.convert(wishComment.getCreator());
        dto.setWish(wish);
        dto.setCreator(creator);
        return dto;
    }
}
