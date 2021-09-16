package ru.iteco.fmh.converter.claim;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dto.claim.ClaimDto;
import ru.iteco.fmh.model.task.claim.Claim;

@Component
@RequiredArgsConstructor
public class ClaimToClaimDtoConverter implements Converter<Claim, ClaimDto> {

    @Override
    public ClaimDto convert(@NonNull Claim claim) {
        ClaimDto claimDto = new ClaimDto();
        BeanUtils.copyProperties(claim, claimDto);

        Integer creatorId = claim.getCreator() != null ? claim.getCreator().getId() : null;
        Integer executorId = claim.getExecutor() != null ? claim.getExecutor().getId() : null;

        claimDto.setCreateDate(claim.getCreateDate() != null ? claim.getCreateDate().toEpochMilli() : null);
        claimDto.setPlanExecuteDate(claim.getPlanExecuteDate() != null ? claim.getPlanExecuteDate().toEpochMilli() : null);
        claimDto.setFactExecuteDate(claim.getFactExecuteDate() != null ? claim.getFactExecuteDate().toEpochMilli() : null);

        claimDto.setCreatorId(creatorId);
        claimDto.setExecutorId(executorId);
        return claimDto;
    }

}
