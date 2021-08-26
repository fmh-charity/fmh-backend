package ru.iteco.fmh.converter.claim.fromClaimDto;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.converter.user.fromUserDto.UserDtoToUserConverter;
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
        User executor = userDtoToUserConverter.convert(dto.getExecutor());
        User creator = userDtoToUserConverter.convert(dto.getCreator());
        claim.setExecutor(executor);
        claim.setCreator(creator);
        return claim;
    }
}
