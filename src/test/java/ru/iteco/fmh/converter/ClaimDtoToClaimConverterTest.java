package ru.iteco.fmh.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.iteco.fmh.converter.claim.fromClaimDto.ClaimDtoToClaimConverter;

import ru.iteco.fmh.converter.user.fromUser.UserToUserDtoConverter;
import ru.iteco.fmh.converter.user.fromUserDto.UserDtoToUserConverter;
import ru.iteco.fmh.dto.claim.ClaimDto;

import ru.iteco.fmh.model.Claim;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.iteco.fmh.TestUtils.getClaimDto;


public class ClaimDtoToClaimConverterTest {


    UserDtoToUserConverter userDtoToUserConverter = new UserDtoToUserConverter();
    ClaimDtoToClaimConverter convert = new ClaimDtoToClaimConverter(userDtoToUserConverter);

    UserToUserDtoConverter userToUserDtoConverter = new UserToUserDtoConverter();

    @Test
    void convert() {
        ClaimDto dto = getClaimDto();

        Claim claim = convert.convert(dto);

        Assertions.assertAll(
                () -> assertEquals(dto.getId(), claim.getId()),
                () -> assertEquals(dto.getDescription(), claim.getDescription()),
                () -> assertEquals(dto.getComment(), claim.getComment()),
                () -> assertEquals(dto.getPlanExecuteDate(), claim.getPlanExecuteDate()),
                () -> assertEquals(dto.getStatus(), claim.getStatus()),
                () -> assertEquals(dto.getCreator(), userToUserDtoConverter.convert(claim.getCreator())),
                () -> assertEquals(dto.getExecutor(), userToUserDtoConverter.convert(claim.getExecutor()))
        );
    }
}
