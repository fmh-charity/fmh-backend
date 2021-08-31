package ru.iteco.fmh.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.iteco.fmh.converter.claim.fromClaim.ClaimCommentToClaimCommentRequestDtoConverter;
import ru.iteco.fmh.converter.claim.fromDto.ClaimCommentRequestDtoToClaimCommentConverter;
import ru.iteco.fmh.dao.repository.ClaimRepository;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dto.claim.ClaimCommentRequestDto;
import ru.iteco.fmh.model.task.claim.Claim;
import ru.iteco.fmh.model.task.claim.ClaimComment;
import ru.iteco.fmh.model.user.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static ru.iteco.fmh.TestUtils.getClaimComment;
import static ru.iteco.fmh.TestUtils.getClaimCommentRequestDto;
import static ru.iteco.fmh.TestUtils.getClaimOpen;
import static ru.iteco.fmh.TestUtils.getUser;

public class ClaimCommentRequestConverterTest {

    ClaimCommentToClaimCommentRequestDtoConverter claimCommentToClaimCommentRequestDtoConverter = new ClaimCommentToClaimCommentRequestDtoConverter();

    UserRepository userRepository = Mockito.mock(UserRepository.class);
    ClaimRepository claimRepository = Mockito.mock(ClaimRepository.class);
    ClaimCommentRequestDtoToClaimCommentConverter claimCommentRequestDtoToClaimCommentConverter =
            new ClaimCommentRequestDtoToClaimCommentConverter(userRepository, claimRepository);

    @Test
    void convertClaimCommentToClaimCommentRequestDto() {
        ClaimComment claimComment = getClaimComment(getClaimOpen());

        ClaimCommentRequestDto dto = claimCommentToClaimCommentRequestDtoConverter.convert(claimComment);
        Assertions.assertAll(
                () -> assertEquals(claimComment.getId(), dto.getId()),
                () -> assertEquals(claimComment.getClaim().getId(), dto.getClaimId()),
                () -> assertEquals(claimComment.getDescription(), dto.getDescription()),
                () -> assertEquals(claimComment.getCreator().getId(), dto.getCreatorId()),
                () -> assertEquals(claimComment.getCreateDate(), dto.getCreateDate())
        );
    }


    @Test
    void convertClaimCommentRequestDtoToClaimCommentWithClaimOpen() {
        ClaimCommentRequestDto dto = getClaimCommentRequestDto();

        User user = getUser();
        Claim claim = getClaimOpen();
        user.setId(dto.getCreatorId());
        claim.setId(dto.getClaimId());

        when(claimRepository.findClaimById(dto.getClaimId())).thenReturn(claim);
        when(userRepository.findUserById(dto.getCreatorId())).thenReturn(user);


        ClaimComment claimComment = claimCommentRequestDtoToClaimCommentConverter.convert(dto);
        Assertions.assertAll(
                () -> assertEquals(dto.getId(), claimComment.getId()),
                () -> assertEquals(dto.getClaimId(), claimComment.getClaim().getId()),
                () -> assertEquals(dto.getDescription(), claimComment.getDescription()),
                () -> assertEquals(dto.getCreatorId(), claimComment.getCreator().getId()),
                () -> assertEquals(dto.getCreateDate(), claimComment.getCreateDate())
        );
    }


}
