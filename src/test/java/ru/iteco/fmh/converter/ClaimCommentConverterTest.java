package ru.iteco.fmh.converter;

import org.junit.jupiter.api.Test;
import ru.iteco.fmh.converter.claim.ClaimCommentDtoToClaimCommentConverter;
import ru.iteco.fmh.converter.claim.ClaimCommentToClaimCommentDtoConverter;
import ru.iteco.fmh.converter.claim.ClaimDtoToClaimConverter;
import ru.iteco.fmh.converter.claim.ClaimToClaimDtoConverter;
import ru.iteco.fmh.converter.user.UserDtoToUserConverter;
import ru.iteco.fmh.converter.user.UserToUserDtoConverter;
import ru.iteco.fmh.dto.claim.ClaimCommentDto;
import ru.iteco.fmh.model.task.claim.ClaimComment;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.iteco.fmh.TestUtils.getClaimComment;
import static ru.iteco.fmh.TestUtils.getClaimCommentDto;
import static ru.iteco.fmh.TestUtils.getClaimDtoInProgress;
import static ru.iteco.fmh.TestUtils.getClaimDtoOpen;
import static ru.iteco.fmh.TestUtils.getClaimInProgress;


public class ClaimCommentConverterTest {

    UserToUserDtoConverter userToUserDtoConverter = new UserToUserDtoConverter();
    ClaimToClaimDtoConverter claimToClaimDtoConverter = new ClaimToClaimDtoConverter(userToUserDtoConverter);
    ClaimCommentToClaimCommentDtoConverter claimCommentToClaimCommentDtoConverter =
            new ClaimCommentToClaimCommentDtoConverter(userToUserDtoConverter, claimToClaimDtoConverter);


    UserDtoToUserConverter userDtoToUserConverter = new UserDtoToUserConverter();
    ClaimDtoToClaimConverter claimDtoToClaimConverter = new ClaimDtoToClaimConverter(userDtoToUserConverter);
    ClaimCommentDtoToClaimCommentConverter claimCommentDtoToClaimCommentConverter =
            new ClaimCommentDtoToClaimCommentConverter(userDtoToUserConverter, claimDtoToClaimConverter);


    @Test
    void convertClaimCommentToClaimCommentDto() {
        ClaimComment claimComment = getClaimComment(getClaimInProgress());

        ClaimCommentDto dto = claimCommentToClaimCommentDtoConverter.convert(claimComment);
        assertAll(
                () -> assertEquals(claimComment.getId(), dto.getId()),
                () -> assertEquals(claimToClaimDtoConverter.convert(claimComment.getClaim()), dto.getClaim()),
                () -> assertEquals(claimComment.getDescription(), dto.getDescription()),
                () -> assertEquals(userToUserDtoConverter.convert(claimComment.getCreator()), dto.getCreator()),
                () -> assertEquals(claimComment.getCreateDate(), dto.getCreateDate())
        );
    }

    @Test
    void convertClaimCommentDtoToClaimCommentWithClaimInProgress() {
        ClaimCommentDto claimCommentDto = getClaimCommentDto(getClaimDtoInProgress());

        ClaimComment claimComment = claimCommentDtoToClaimCommentConverter.convert(claimCommentDto);
        assertAll(
                () -> assertEquals(claimCommentDto.getId(), claimComment.getId()),
                () -> assertEquals(claimCommentDto.getClaim(), claimToClaimDtoConverter.convert(claimComment.getClaim())),
                () -> assertEquals(claimCommentDto.getDescription(), claimComment.getDescription()),
                () -> assertEquals(claimCommentDto.getCreator(), userToUserDtoConverter.convert(claimComment.getCreator())),
                () -> assertEquals(claimCommentDto.getCreateDate(), claimComment.getCreateDate())
        );
    }

    @Test
    void convertClaimCommentDtoToClaimCommentWithClaimOpen() {
        ClaimCommentDto claimCommentDto = getClaimCommentDto(getClaimDtoOpen());

        ClaimComment claimComment = claimCommentDtoToClaimCommentConverter.convert(claimCommentDto);
        assertAll(
                () -> assertEquals(claimCommentDto.getId(), claimComment.getId()),
                () -> assertEquals(claimCommentDto.getClaim(), claimToClaimDtoConverter.convert(claimComment.getClaim())),
                () -> assertEquals(claimCommentDto.getDescription(), claimComment.getDescription()),
                () -> assertEquals(claimCommentDto.getCreator(), userToUserDtoConverter.convert(claimComment.getCreator())),
                () -> assertEquals(claimCommentDto.getCreateDate(), claimComment.getCreateDate())
        );
    }


}
