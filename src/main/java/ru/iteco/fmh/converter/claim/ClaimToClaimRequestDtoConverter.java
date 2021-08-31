package ru.iteco.fmh.converter.claim.fromClaim;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dto.claim.ClaimRequestDto;
import ru.iteco.fmh.model.task.claim.Claim;
import ru.iteco.fmh.model.user.User;

@Component
@RequiredArgsConstructor
public class ClaimToClaimRequestDtoConverter implements Converter<Claim, ClaimRequestDto> {

    @Override
    public ClaimRequestDto convert(@NonNull Claim claim) {
        ClaimRequestDto claimRequestDto = new ClaimRequestDto();
        BeanUtils.copyProperties(claim, claimRequestDto);

        Integer creatorId = claim.getCreator() != null ? claim.getCreator().getId() : null;
        Integer executorId = claim.getExecutor() != null ? claim.getExecutor().getId() : null;

        claimRequestDto.setCreatorId(creatorId);
        claimRequestDto.setExecutorId(executorId);
        return claimRequestDto;
    }

}
