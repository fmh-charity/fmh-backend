package ru.iteco.fmh.converter.wish;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dto.wish.WishCreationRequest;
import ru.iteco.fmh.model.wish.Wish;

import java.time.Instant;

import static ru.iteco.fmh.model.wish.Status.OPEN;

@Component
@RequiredArgsConstructor
public class WishCreationRequestToWishConverter implements Converter<WishCreationRequest, Wish> {

    @Override
    public Wish convert(@NonNull WishCreationRequest wishCreationRequest) {
        Wish wish = new Wish();
        BeanUtils.copyProperties(wishCreationRequest, wish);
        wish.setCreateDate(Instant.now());
        wish.setPlanExecuteDate(wishCreationRequest.getPlanExecuteDate() != null
                ? Instant.ofEpochMilli(wishCreationRequest.getPlanExecuteDate()) : null);
        wish.setStatus(OPEN);

        return wish;
    }
}
