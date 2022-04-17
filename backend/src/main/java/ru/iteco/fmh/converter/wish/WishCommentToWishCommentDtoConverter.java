package ru.iteco.fmh.converter.wish;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dto.wish.WishCommentDto;
import ru.iteco.fmh.model.task.wish.WishComment;

@Component
@RequiredArgsConstructor
public class WishCommentToWishCommentDtoConverter implements Converter<WishComment, WishCommentDto> {

    @Override
    public WishCommentDto convert(@NonNull WishComment wishComment) {
        WishCommentDto dto = new WishCommentDto();
        BeanUtils.copyProperties(wishComment, dto);

        Integer wishId = wishComment.getWish() != null ? wishComment.getWish().getId() : null;
        Integer creatorId = wishComment.getCreator() != null ? wishComment.getCreator().getId() : null;
        dto.setCreateDate(wishComment.getCreateDate() != null ? wishComment.getCreateDate().toEpochMilli() : null);

        dto.setWishId(wishId);
        dto.setCreatorId(creatorId);
        return dto;
    }
}

