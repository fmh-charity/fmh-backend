package ru.iteco.fmh.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.iteco.fmh.TestUtils;
import ru.iteco.fmh.converter.claim.fromClaim.ClaimToClaimDtoConverter;
import ru.iteco.fmh.converter.user.fromUser.UserToUserDtoConverter;
import ru.iteco.fmh.dto.claim.ClaimDto;
import ru.iteco.fmh.model.task.StatusE;
import ru.iteco.fmh.model.task.claim.Claim;


import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ClaimToClaimDtoConverterTest {


    UserToUserDtoConverter userToUserDtoConverter = new UserToUserDtoConverter();
    ClaimToClaimDtoConverter convertor = new ClaimToClaimDtoConverter(userToUserDtoConverter);

    @Test
    void convertExecutorNull() {
        Claim claim = getClaim();
        ClaimDto dto = convertor.convert(claim);
        Assertions.assertAll(
                () -> assertEquals(claim.getId(), dto.getId()),
                () -> assertEquals(claim.getTitle(), dto.getTitle()),
                () -> assertEquals(claim.getDescription(), dto.getDescription()),
                () -> assertEquals(userToUserDtoConverter.convert(claim.getCreator()), dto.getCreator()),
                () -> assertEquals(claim.getCreateDate(), dto.getCreateDate()),
                () -> assertEquals(claim.getFactExecuteDate(), dto.getFactExecuteDate()),
                () -> assertEquals(claim.getPlanExecuteDate(), dto.getPlanExecuteDate()),
                () -> assertEquals(claim.getStatus(), dto.getStatus()),
                () -> assertNull(dto.getExecutor())

        );

    }
    public static Claim getClaim() {

        return Claim.builder()
                .id(26)
                .title("title")
                .description("description")
                .creator(TestUtils.getUser())
                .executor(null)
                .createDate(java.time.LocalDateTime.now())
                .planExecuteDate(java.time.LocalDateTime.now())
                .factExecuteDate(null)
                .status(ru.iteco.fmh.model.task.StatusE.OPEN)
                .build();
    }

    @Test
    void convert() {
        Claim claim = getClaim2();
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
    public static Claim getClaim2() {

        return Claim.builder()
                .id(27)
                .title("title")
                .description("description")
                .creator(TestUtils.getUser())
                .executor(TestUtils.getUser())
                .createDate(java.time.LocalDateTime.now())
                .planExecuteDate(java.time.LocalDateTime.now())
                .factExecuteDate(null)
                .status(StatusE.IN_PROGRESS)
                .build();
    }

}
