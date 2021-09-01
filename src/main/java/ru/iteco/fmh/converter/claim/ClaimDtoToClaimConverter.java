package ru.iteco.fmh.converter.claim;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.converter.user.UserDtoToUserConverter;
import ru.iteco.fmh.dto.claim.ClaimDto;
import ru.iteco.fmh.model.task.claim.Claim;
import ru.iteco.fmh.model.user.User;

@Component
@RequiredArgsConstructor
public class ClaimDtoToClaimConverter implements Converter<ClaimDto, Claim> {

    private final UserDtoToUserConverter userDtoToUserConverter;

    @Override
    public Claim convert(@NonNull ClaimDto dto) {
        Claim claim = new Claim();
        BeanUtils.copyProperties(dto, claim);

        User creator = dto.getCreator() != null
                ? userDtoToUserConverter.convert(dto.getCreator()) : null;
        User executor = dto.getExecutor() != null
                ? userDtoToUserConverter.convert(dto.getExecutor()) : null;

        claim.setExecutor(executor);
        claim.setCreator(creator);
        return claim;
    }
}
