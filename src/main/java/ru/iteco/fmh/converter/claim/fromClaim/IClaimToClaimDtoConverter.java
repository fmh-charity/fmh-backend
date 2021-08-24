package ru.iteco.fmh.converter.claim.fromClaim;

import ru.iteco.fmh.dto.claim.ClaimCommentDto;
import ru.iteco.fmh.dto.claim.ClaimDto;
import ru.iteco.fmh.dto.user.UserDto;
import ru.iteco.fmh.model.task.claim.Claim;
import ru.iteco.fmh.model.task.claim.ClaimComment;
import ru.iteco.fmh.model.user.User;

public interface IClaimToClaimDtoConverter {

    ClaimDto convert(Claim claim);
}
