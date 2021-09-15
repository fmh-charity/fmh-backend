package ru.iteco.fmh.converter.wish;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dao.repository.WishRepository;
import ru.iteco.fmh.dto.wish.WishCommentDto;
import ru.iteco.fmh.model.task.wish.Wish;
import ru.iteco.fmh.model.task.wish.WishComment;
import ru.iteco.fmh.model.user.User;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class WishCommentDtoToWishCommentConverter implements Converter<WishCommentDto, WishComment> {

    private final UserRepository userRepository;
    private final WishRepository wishRepository;

    @Override
    public WishComment convert(@NonNull WishCommentDto dto) {
        WishComment wishComment = new WishComment();
        BeanUtils.copyProperties(dto, wishComment);

        Wish wish = dto.getWishId() != null ? wishRepository.findWishById(dto.getWishId()) : null;
        User creator = dto.getCreatorId() != null ? userRepository.findUserById(dto.getCreatorId()) : null;

        wishComment.setCreateDate(dto.getCreateDate() != null
                ? Instant.ofEpochMilli(dto.getCreateDate()) : null);

        wishComment.setWish(wish);
        wishComment.setCreator(creator);
        return wishComment;
    }
}



