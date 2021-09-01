package ru.iteco.fmh.converter.claim;

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
public class ClaimRequestDtoToClaimConverter implements Converter<ClaimRequestDto, Claim> {

    private final UserRepository userRepository;

    @Override
    public Claim convert(@NonNull ClaimRequestDto claimRequestDto) {

        Claim claim = new Claim();
        BeanUtils.copyProperties(claimRequestDto, claim);

        User creator = claimRequestDto.getCreatorId() != null ? userRepository.findUserById(claimRequestDto.getCreatorId()) : null;
        User executor = claimRequestDto.getExecutorId() != null ? userRepository.findUserById(claimRequestDto.getExecutorId()) : null;

        claim.setCreator(creator);
        claim.setExecutor(executor);
        return claim;
    }
}
