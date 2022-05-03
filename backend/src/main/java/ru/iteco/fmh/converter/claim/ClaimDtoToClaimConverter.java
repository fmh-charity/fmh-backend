package ru.iteco.fmh.converter.claim;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dto.claim.ClaimDto;
import ru.iteco.fmh.model.task.claim.Claim;
import ru.iteco.fmh.model.user.User;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class ClaimDtoToClaimConverter implements Converter<ClaimDto, Claim> {

    private final UserRepository userRepository;

    @Override
    public Claim convert(@NonNull ClaimDto claimDto) {

        Claim claim = new Claim();
        BeanUtils.copyProperties(claimDto, claim);

        User creator = claimDto.getCreatorId() != null ? userRepository.findUserById(claimDto.getCreatorId()) : null;
        User executor = claimDto.getExecutorId() != null ? userRepository.findUserById(claimDto.getExecutorId()) : null;

        claim.setCreateDate(claimDto.getCreateDate() != null ? Instant.ofEpochMilli(claimDto.getCreateDate()) : null);
        claim.setPlanExecuteDate(claimDto.getPlanExecuteDate() != null ? Instant.ofEpochMilli(claimDto.getPlanExecuteDate()) : null);
        claim.setFactExecuteDate(claimDto.getFactExecuteDate() != null ? Instant.ofEpochMilli(claimDto.getFactExecuteDate()) : null);

        claim.setCreator(creator);
        claim.setExecutor(executor);
        return claim;
    }
}
