package ru.iteco.fmh.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.iteco.fmh.converter.claim.fromClaim.ClaimCommentToClaimCommentRequestDtoConverter;
import ru.iteco.fmh.converter.claim.fromClaim.ClaimToClaimDtoConverter;
import ru.iteco.fmh.converter.claim.fromClaim.ClaimCommentToClaimCommentDtoConverter;
import ru.iteco.fmh.converter.claim.fromDto.ClaimCommentDtoToClaimCommentConverter;
import ru.iteco.fmh.converter.claim.fromDto.ClaimCommentRequestDtoToClaimCommentConverter;
import ru.iteco.fmh.converter.claim.fromDto.ClaimDtoToClaimConverter;
import ru.iteco.fmh.converter.user.fromUser.UserToUserDtoConverter;
import ru.iteco.fmh.converter.user.fromUserDto.UserDtoToUserConverter;
import ru.iteco.fmh.dao.repository.ClaimRepository;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dto.claim.ClaimCommentDto;

import ru.iteco.fmh.dto.claim.ClaimCommentRequestDto;
import ru.iteco.fmh.model.task.claim.Claim;
import ru.iteco.fmh.model.task.claim.ClaimComment;
import ru.iteco.fmh.model.user.User;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
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


    ClaimCommentToClaimCommentRequestDtoConverter converter = new ClaimCommentToClaimCommentRequestDtoConverter();
    UserRepository userRepository = Mockito.mock(UserRepository.class);
    ClaimRepository claimRepository = Mockito.mock(ClaimRepository.class);
    ClaimCommentRequestDtoToClaimCommentConverter converter2 =
            new ClaimCommentRequestDtoToClaimCommentConverter (userRepository,claimRepository);

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

    @Test
    void convertClaimCommentDtoToClaimCommentOpen() {
        ClaimCommentDto claimCommentDto = ClaimCommentDto.builder()
                .id(24)
                .claim(getClaimDtoOpen())
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



    @Test
    void convertClaimCommentToClaimCommentRequestDto() {
        ClaimComment claimComment = ClaimComment.builder()
                .id(23)
                .claim(getClaimOpen())
                .creator(getUser())
                .description("description")
                .createDate(LocalDateTime.now())
                .build();

        ClaimCommentRequestDto dto = converter.convert(claimComment);
        Assertions.assertAll(
                () -> assertEquals(claimComment.getId(), dto.getId()),
                () -> assertEquals(claimComment.getClaim().getId(),dto.getClaimId()),
                () -> assertEquals(claimComment.getDescription(), dto.getDescription()),
                () -> assertEquals(claimComment.getCreator().getId(), dto.getCreatorId()),
                () -> assertEquals(claimComment.getCreateDate(), dto.getCreateDate())
        );
    }


    @Test
    void convertClaimCommentRequestDtoToClaimCommentOpen() {
        ClaimCommentRequestDto dto = ClaimCommentRequestDto.builder()
                .id(24)
                .claimId(2)
                .creatorId(2)
                .description("description")
                .createDate(LocalDateTime.now())
                .build();

        User user = getUser();
        Claim claim = getClaimOpen();
        user.setId(dto.getCreatorId());
        claim.setId(dto.getClaimId());

        when(claimRepository.findClaimById(dto.getClaimId())).thenReturn(claim);
        when(userRepository.findUserById(dto.getCreatorId())).thenReturn(user);


        ClaimComment claimComment = converter2.convert(dto);
        Assertions.assertAll(
                () -> assertEquals(dto.getId(), claimComment.getId()),
                () -> assertEquals(dto.getClaimId(),claimComment.getClaim().getId()),
                () -> assertEquals(dto.getDescription(), claimComment.getDescription()),
                () -> assertEquals(dto.getCreatorId(), claimComment.getCreator().getId()),
                () -> assertEquals(dto.getCreateDate(), claimComment.getCreateDate())
        );
    }




}
