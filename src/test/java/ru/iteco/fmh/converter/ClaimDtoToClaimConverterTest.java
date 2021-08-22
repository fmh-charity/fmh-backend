package ru.iteco.fmh.converter;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.iteco.fmh.converter.claim.fromClaimDto.ClaimDtoToClaimConverter;

import ru.iteco.fmh.converter.user.fromUser.UserToUserDtoConverter;
import ru.iteco.fmh.converter.user.fromUserDto.UserDtoToUserConverter;
import ru.iteco.fmh.dto.claim.ClaimDto;


import ru.iteco.fmh.model.task.StatusE;
import ru.iteco.fmh.model.task.claim.Claim;


import java.time.LocalDateTime;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.iteco.fmh.converter.claim.fromClaimDto.ClaimDtoToClaimConverter.getUserDto;


public class ClaimDtoToClaimConverterTest {


    UserDtoToUserConverter userDtoToUserConverter = new UserDtoToUserConverter();
    ClaimDtoToClaimConverter convert = new ClaimDtoToClaimConverter(userDtoToUserConverter);

    UserToUserDtoConverter userToUserDtoConverter = new UserToUserDtoConverter();

    @Test
    void convert() {
        ClaimDto dto = getClaimDto2();

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

    public static ClaimDto getClaimDto2() {

        return ClaimDto.builder()
                .id(27)
                .title("Title")
                .description("description")
                .creator(getUserDto())
                .executor(getUserDto())
                .planExecuteDate(LocalDateTime.now().withNano(0))
                .createDate(LocalDateTime.now().plusDays(2).withNano(0))
                .factExecuteDate(null)
                .status(StatusE.IN_PROGRESS)
                .build();
    }

    //проверка конвертора если executor = null
//    @Test
//    void convertExecutorNull() {
//        ClaimDto dto = getClaimDto();
//
//        Claim claim = convert.convert(dto);
//        System.out.println(claim.getId());
//        Assertions.assertAll(
//                () -> assertEquals(dto.getId(), claim.getId()),
//                () -> assertEquals(dto.getTitle(), claim.getTitle()),
//                () -> assertEquals(dto.getDescription(), claim.getDescription()),
//                () -> assertEquals(dto.getPlanExecuteDate(), claim.getPlanExecuteDate()),
//                () -> assertEquals(dto.getCreateDate(), claim.getCreateDate()),
//                () -> assertEquals(dto.getFactExecuteDate(), claim.getFactExecuteDate()),
//                () -> assertEquals(dto.getStatus(), claim.getStatus()),
//                () -> assertEquals(dto.getCreator(), userToUserDtoConverter.convert(claim.getCreator())),
//                () -> assertNull(claim.getExecutor())
//        );
//    }
//
//    public static ClaimDto getClaimDto() {
//
//        return ClaimDto.builder()
//                .id(26)
//                .title("Title")
//                .description("description")
//                .creator(getUserDto())
//                .executor(null)
//                .planExecuteDate(LocalDateTime.now().withNano(0))
//                .createDate(LocalDateTime.now().plusDays(2).withNano(0))
//                .factExecuteDate(null)
//                .status(StatusE.OPEN)
//                .build();
//    }
}






