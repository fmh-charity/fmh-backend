package ru.iteco.fmh.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.iteco.fmh.converter.claim.fromClaim.ClaimToClaimShortInfoDtoConverter;
import ru.iteco.fmh.dto.claim.ClaimShortInfoDto;
import ru.iteco.fmh.model.Claim;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.iteco.fmh.TestUtils.getClaim;


public class ClaimToClaimShortInfoDtoConverterTest {

    ClaimToClaimShortInfoDtoConverter convertor = new ClaimToClaimShortInfoDtoConverter();
    @Test
    void convert() {
        Claim claim = getClaim();
        ClaimShortInfoDto shortInfoDto = convertor.convert(claim);
        Assertions.assertAll(
                () -> assertEquals(claim.getId(), shortInfoDto.getId()),
                () -> assertEquals(claim.getPlanExecuteDate(), shortInfoDto.getPlanExecuteDate()),
                () -> assertEquals(claim.getFactExecuteDate(), shortInfoDto.getFactExecuteDate()),
                () -> assertEquals(claim.getExecutor().getShortUserName(), shortInfoDto.getShortExecutorName()),
                () ->  assertEquals(claim.getStatus(),shortInfoDto.getStatus()),
                () ->  assertEquals(claim.getDescription(),shortInfoDto.getDescription())
        );
    }
}
