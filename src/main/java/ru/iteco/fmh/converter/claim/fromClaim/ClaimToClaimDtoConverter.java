package ru.iteco.fmh.converter.claim.fromClaim;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.converter.user.fromUser.UserToUserDtoConverter;
import ru.iteco.fmh.dto.claim.ClaimDto;
import ru.iteco.fmh.dto.user.UserDto;
import ru.iteco.fmh.model.task.claim.Claim;

@Component
@RequiredArgsConstructor
public class ClaimToClaimDtoConverter implements Converter<Claim, ClaimDto> {

    private final UserToUserDtoConverter userToUserDtoConverter;

    @Override
    public ClaimDto convert(@NonNull Claim claim) {
        ClaimDto dto = new ClaimDto();
        BeanUtils.copyProperties(claim, dto);

        UserDto creator = userToUserDtoConverter.convert(claim.getCreator());
        UserDto executor = claim.getExecutor()!=null? userToUserDtoConverter.convert(claim.getExecutor()) : null;

        dto.setExecutor(executor);
        dto.setCreator(creator);
        return dto;
    }

}
