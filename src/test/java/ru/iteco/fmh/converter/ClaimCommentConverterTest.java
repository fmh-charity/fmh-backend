package ru.iteco.fmh.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.iteco.fmh.converter.claim.fromClaim.ClaimToClaimDtoConverter;
import ru.iteco.fmh.converter.claim.fromClaimComment.ClaimCommentToClaimCommentDtoConverter;
import ru.iteco.fmh.converter.claim.fromClaimCommentDto.ClaimCommentDtoToClaimCommentConverter;
import ru.iteco.fmh.converter.claim.fromClaimDto.ClaimDtoToClaimConverter;
import ru.iteco.fmh.converter.user.fromUser.UserToUserDtoConverter;
import ru.iteco.fmh.converter.user.fromUserDto.UserDtoToUserConverter;
import ru.iteco.fmh.dto.claim.ClaimCommentDto;

import ru.iteco.fmh.model.task.claim.ClaimComment;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.iteco.fmh.TestUtils.*;


public class ClaimCommentConverterTest {

    UserToUserDtoConverter userToUserDtoConverter = new UserToUserDtoConverter();
    ClaimToClaimDtoConverter claimToClaimDtoConverter = new ClaimToClaimDtoConverter(userToUserDtoConverter);
    ClaimCommentToClaimCommentDtoConverter claimCommentToClaimCommentDtoConverter =
            new ClaimCommentToClaimCommentDtoConverter(userToUserDtoConverter, claimToClaimDtoConverter );


    UserDtoToUserConverter userDtoToUserConverter = new UserDtoToUserConverter();
    ClaimDtoToClaimConverter claimDtoToClaimConverter = new ClaimDtoToClaimConverter(userDtoToUserConverter);
    ClaimCommentDtoToClaimCommentConverter claimCommentDtoToClaimCommentConverter =
            new ClaimCommentDtoToClaimCommentConverter(userDtoToUserConverter,claimDtoToClaimConverter);

    @Test
    void convertClaimCommentToClaimCommentDto() {
        ClaimComment claimComment = ClaimComment.builder()
                .id(23)
                .claim(getClaimInProgress())
                .creator(getUser())
                .description("description")
                .createDate(LocalDateTime.now())
                .build();

        ClaimCommentDto dto = claimCommentToClaimCommentDtoConverter.convert(claimComment);
        Assertions.assertAll(
                () -> assertEquals(claimComment.getId(), dto.getId()),
                () -> assertEquals(claimToClaimDtoConverter.convert(claimComment.getClaim()),dto.getClaim()),
                () -> assertEquals(claimComment.getDescription(), dto.getDescription()),
                () -> assertEquals(userToUserDtoConverter.convert(claimComment.getCreator()), dto.getCreator()),
                () -> assertEquals(claimComment.getCreateDate(), dto.getCreateDate())
        );
    }

    @Test
    void convertClaimCommentDtoToClaimComment() {
        ClaimCommentDto claimCommentDto = ClaimCommentDto.builder()
                .id(24)
                .claim(getClaimDtoInProgress())
                .creator(getUserDto())
                .description("description")
                .createDate(LocalDateTime.now())
                .build();

        ClaimComment claimComment = claimCommentDtoToClaimCommentConverter.convert(claimCommentDto);
        Assertions.assertAll(
                () -> assertEquals(claimCommentDto.getId(), claimComment.getId()),
                () -> assertEquals(claimCommentDto.getClaim(),claimToClaimDtoConverter.convert(claimComment.getClaim())),
                () -> assertEquals(claimCommentDto.getDescription(), claimComment.getDescription()),
                () -> assertEquals(claimCommentDto.getCreator(), userToUserDtoConverter.convert(claimComment.getCreator())),
                () -> assertEquals(claimCommentDto.getCreateDate(), claimComment.getCreateDate())
        );
    }

}
