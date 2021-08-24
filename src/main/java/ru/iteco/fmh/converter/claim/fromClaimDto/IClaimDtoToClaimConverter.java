package ru.iteco.fmh.converter.claim.fromClaimDto;

import ru.iteco.fmh.dto.claim.ClaimDto;
import ru.iteco.fmh.model.task.claim.Claim;

public interface IClaimDtoToClaimConverter {
    Claim convert(ClaimDto claimDto);
}
