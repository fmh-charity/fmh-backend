package ru.iteco.fmh.converter;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.iteco.fmh.converter.claim.fromClaimDto.ClaimDtoToClaimConverter;

import ru.iteco.fmh.converter.user.fromUser.UserToUserDtoConverter;
import ru.iteco.fmh.converter.user.fromUserDto.UserDtoToUserConverter;
import ru.iteco.fmh.dto.claim.ClaimDto;
import ru.iteco.fmh.model.task.claim.Claim;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static ru.iteco.fmh.TestUtils.getClaimDtoInProgress;
import static ru.iteco.fmh.TestUtils.getClaimDtoOpen;


public class ClaimDtoToClaimConverterTest {

    UserDtoToUserConverter userDtoToUserConverter = new UserDtoToUserConverter();
    ClaimDtoToClaimConverter convert = new ClaimDtoToClaimConverter(userDtoToUserConverter);

    UserToUserDtoConverter userToUserDtoConverter = new UserToUserDtoConverter();

    @Test
    void convert() {
        ClaimDto dto = getClaimDtoInProgress();

        Claim claim = convert.convert(dto);

        Assertions.assertAll(
                () -> assertEquals(dto.getId(), claim.getId()),
                () -> assertEquals(dto.getTitle(), claim.getTitle()),
                () -> assertEquals(dto.getDescription(), claim.getDescription()),
                () -> assertEquals(dto.getPlanExecuteDate(), claim.getPlanExecuteDate()),
                () -> assertEquals(dto.getCreateDate(), claim.getCreateDate()),
                () -> assertEquals(dto.getFactExecuteDate(), claim.getFactExecuteDate()),
                () -> assertEquals(dto.getStatus(), claim.getStatus()),
                () -> assertEquals(dto.getCreator(), userToUserDtoConverter.convert(claim.getCreator())),
                () -> assertEquals(dto.getExecutor(), userToUserDtoConverter.convert(claim.getExecutor()))
        );
    }

    @Test
    void convertForOpen() {
        ClaimDto dto = getClaimDtoOpen();

        Claim claim = convert.convert(dto);

        Assertions.assertAll(
                () -> assertEquals(dto.getId(), claim.getId()),
                () -> assertEquals(dto.getTitle(), claim.getTitle()),
                () -> assertEquals(dto.getDescription(), claim.getDescription()),
                () -> assertEquals(dto.getPlanExecuteDate(), claim.getPlanExecuteDate()),
                () -> assertEquals(dto.getCreateDate(), claim.getCreateDate()),
                () -> assertEquals(dto.getFactExecuteDate(), claim.getFactExecuteDate()),
                () -> assertEquals(dto.getStatus(), claim.getStatus()),
                () -> assertEquals(dto.getCreator(), userToUserDtoConverter.convert(claim.getCreator())),
                () -> assertEquals(dto.getExecutor(), claim.getExecutor()),
                () -> assertNull(dto.getExecutor()),
                () -> assertNull(claim.getExecutor())
        );
    }


}






