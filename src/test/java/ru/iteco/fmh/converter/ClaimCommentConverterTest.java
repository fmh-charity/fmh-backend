package ru.iteco.fmh.converter;

import org.junit.jupiter.api.Test;
import ru.iteco.fmh.converter.claim.ClaimCommentDtoToClaimCommentConverter;
import ru.iteco.fmh.converter.claim.ClaimCommentToClaimCommentDtoConverter;
import ru.iteco.fmh.dao.repository.ClaimRepository;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dto.claim.ClaimCommentDto;
import ru.iteco.fmh.model.task.claim.Claim;
import ru.iteco.fmh.model.task.claim.ClaimComment;
import ru.iteco.fmh.model.user.User;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.iteco.fmh.TestUtils.getClaimComment;
import static ru.iteco.fmh.TestUtils.getClaimCommentDto;
import static ru.iteco.fmh.TestUtils.getClaimOpen;
import static ru.iteco.fmh.TestUtils.getUser;

public class ClaimCommentConverterTest {

    ClaimCommentToClaimCommentDtoConverter claimCommentToClaimCommentDtoConverter = new ClaimCommentToClaimCommentDtoConverter();

    UserRepository userRepository = mock(UserRepository.class);
    ClaimRepository claimRepository = mock(ClaimRepository.class);
    ClaimCommentDtoToClaimCommentConverter claimCommentDtoToClaimCommentConverter =
            new ClaimCommentDtoToClaimCommentConverter(userRepository, claimRepository);

    @Test
    void convertClaimCommentToClaimCommentRequestDto() {
        ClaimComment claimComment = getClaimComment(getClaimOpen());

        ClaimCommentDto dto = claimCommentToClaimCommentDtoConverter.convert(claimComment);
        assertAll(
                () -> assertEquals(claimComment.getId(), dto.getId()),
                () -> assertEquals(claimComment.getClaim().getId(), dto.getClaimId()),
                () -> assertEquals(claimComment.getDescription(), dto.getDescription()),
                () -> assertEquals(claimComment.getCreator().getId(), dto.getCreatorId()),
                () -> assertEquals(claimComment.getCreateDate(), dto.getCreateDate())
        );
    }


    @Test
    void convertClaimCommentRequestDtoToClaimCommentWithClaimOpen() {
        ClaimCommentDto dto = getClaimCommentDto();

        User user = getUser();
        Claim claim = getClaimOpen();
        user.setId(dto.getCreatorId());
        claim.setId(dto.getClaimId());

        when(claimRepository.findClaimById(dto.getClaimId())).thenReturn(claim);
        when(userRepository.findUserById(dto.getCreatorId())).thenReturn(user);


        ClaimComment claimComment = claimCommentDtoToClaimCommentConverter.convert(dto);
        assertAll(
                () -> assertEquals(dto.getId(), claimComment.getId()),
                () -> assertEquals(dto.getClaimId(), claimComment.getClaim().getId()),
                () -> assertEquals(dto.getDescription(), claimComment.getDescription()),
                () -> assertEquals(dto.getCreatorId(), claimComment.getCreator().getId()),
                () -> assertEquals(dto.getCreateDate(), claimComment.getCreateDate())
        );
    }


}
