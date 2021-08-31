package ru.iteco.fmh.converter;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import ru.iteco.fmh.converter.claim.fromDto.ClaimDtoToClaimConverter;

import ru.iteco.fmh.converter.claim.fromDto.ClaimRequestDtoToClaimConverter;
import ru.iteco.fmh.converter.user.fromUser.UserToUserDtoConverter;
import ru.iteco.fmh.converter.user.fromUserDto.UserDtoToUserConverter;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dto.claim.ClaimDto;
import ru.iteco.fmh.dto.claim.ClaimRequestDto;

import ru.iteco.fmh.model.task.claim.Claim;

import ru.iteco.fmh.model.user.User;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static ru.iteco.fmh.TestUtils.*;



public class ClaimDtoToClaimConverterTest {

    UserDtoToUserConverter userDtoToUserConverter = new UserDtoToUserConverter();
    ClaimDtoToClaimConverter convert = new ClaimDtoToClaimConverter(userDtoToUserConverter);


    UserRepository userRepository = Mockito.mock(UserRepository.class);
    ClaimRequestDtoToClaimConverter converter = new ClaimRequestDtoToClaimConverter(userRepository);

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


    @Test
    void convertClaimRequestDtoForOpen() {
        ClaimRequestDto dto = getClaimRequestDtoOpen();
        User user = getUser();
        user.setId(dto.getCreatorId());

        when(userRepository.findUserById(dto.getCreatorId())).thenReturn(user);
        when(userRepository.findUserById(dto.getExecutorId())).thenReturn(null);

        Claim claim = converter.convert(dto);

        Assertions.assertAll(
                () -> assertEquals(dto.getId(), claim.getId()),
                () -> assertEquals(dto.getTitle(), claim.getTitle()),
                () -> assertEquals(dto.getDescription(), claim.getDescription()),
                () -> assertEquals(dto.getPlanExecuteDate(), claim.getPlanExecuteDate()),
                () -> assertEquals(dto.getCreateDate(), claim.getCreateDate()),
                () -> assertEquals(dto.getFactExecuteDate(), claim.getFactExecuteDate()),
                () -> assertEquals(dto.getStatus(), claim.getStatus()),
                () -> assertEquals(dto.getCreatorId(), claim.getCreator().getId()),
                () -> assertEquals(dto.getExecutorId(), claim.getExecutor()),
                () -> assertNull(dto.getExecutorId()),
                () -> assertNull(claim.getExecutor())
        );
    }


    @Test
    void convertClaimRequestDtoForInProgress() {
        ClaimRequestDto dto = getClaimRequestDtoInProgress();
        User user = getUser();
        user.setId(dto.getCreatorId());

        when(userRepository.findUserById(any())).thenReturn(user);

        Claim claim = converter.convert(dto);

        Assertions.assertAll(
                () -> assertEquals(dto.getId(), claim.getId()),
                () -> assertEquals(dto.getTitle(), claim.getTitle()),
                () -> assertEquals(dto.getDescription(), claim.getDescription()),
                () -> assertEquals(dto.getPlanExecuteDate(), claim.getPlanExecuteDate()),
                () -> assertEquals(dto.getCreateDate(), claim.getCreateDate()),
                () -> assertEquals(dto.getFactExecuteDate(), claim.getFactExecuteDate()),
                () -> assertEquals(dto.getStatus(), claim.getStatus()),
                () -> assertEquals(dto.getCreatorId(), claim.getCreator().getId()),
                () -> assertEquals(dto.getExecutorId(), claim.getExecutor().getId()),
                () -> assertNotNull(dto.getExecutorId()),
                () -> assertNotNull(claim.getExecutor())
        );
    }





}






