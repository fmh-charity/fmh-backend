package ru.iteco.fmh.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.iteco.fmh.converter.claim.fromClaim.ClaimToClaimDtoConverter;
import ru.iteco.fmh.converter.user.fromUser.UserToUserDtoConverter;
import ru.iteco.fmh.dto.claim.ClaimDto;
import ru.iteco.fmh.model.task.claim.Claim;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static ru.iteco.fmh.TestUtils.getClaimInProgress;
import static ru.iteco.fmh.TestUtils.getClaimOpen;


public class ClaimToClaimDtoConverterTest {


    UserToUserDtoConverter userToUserDtoConverter = new UserToUserDtoConverter();
    ClaimToClaimDtoConverter convertor = new ClaimToClaimDtoConverter(userToUserDtoConverter);

    @Test
    void convert() {
        Claim claim = getClaimInProgress();
        ClaimDto dto = convertor.convert(claim);
        Assertions.assertAll(
                () -> assertEquals(claim.getId(), dto.getId()),
                () -> assertEquals(claim.getTitle(), dto.getTitle()),
                () -> assertEquals(claim.getDescription(), dto.getDescription()),
                () -> assertEquals(userToUserDtoConverter.convert(claim.getCreator()), dto.getCreator()),
                () -> assertEquals(userToUserDtoConverter.convert(claim.getExecutor()), dto.getExecutor()),
                () -> assertEquals(claim.getCreateDate(), dto.getCreateDate()),
                () -> assertEquals(claim.getFactExecuteDate(), dto.getFactExecuteDate()),
                () -> assertEquals(claim.getPlanExecuteDate(), dto.getPlanExecuteDate()),
                () -> assertEquals(claim.getStatus(), dto.getStatus())

        );
    }

    @Test
    void convertOpen() {
        Claim claim = getClaimOpen();
        ClaimDto dto = convertor.convert(claim);
        Assertions.assertAll(
                () -> assertEquals(claim.getId(), dto.getId()),
                () -> assertEquals(claim.getTitle(), dto.getTitle()),
                () -> assertEquals(claim.getDescription(), dto.getDescription()),
                () -> assertEquals(userToUserDtoConverter.convert(claim.getCreator()), dto.getCreator()),
                () -> assertEquals(claim.getExecutor(), dto.getExecutor()),
                () -> assertNull(claim.getExecutor()),
                () -> assertNull(dto.getExecutor()),
                () -> assertEquals(claim.getCreateDate(), dto.getCreateDate()),
                () -> assertEquals(claim.getFactExecuteDate(), dto.getFactExecuteDate()),
                () -> assertEquals(claim.getPlanExecuteDate(), dto.getPlanExecuteDate()),
                () -> assertEquals(claim.getStatus(), dto.getStatus())

        );
    }

}
