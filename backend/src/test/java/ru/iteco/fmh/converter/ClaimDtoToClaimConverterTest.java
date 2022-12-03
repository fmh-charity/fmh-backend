package ru.iteco.fmh.converter;


import org.junit.jupiter.api.Test;
import ru.iteco.fmh.converter.claim.ClaimDtoToClaimConverter;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dto.claim.ClaimDto;
import ru.iteco.fmh.model.task.claim.Claim;
import ru.iteco.fmh.model.user.User;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.iteco.fmh.TestUtils.*;


public class ClaimDtoToClaimConverterTest {


    UserRepository userRepository = mock(UserRepository.class);
    ClaimDtoToClaimConverter converter = new ClaimDtoToClaimConverter(userRepository);


    @Test
    void convertClaimRequestDtoWithStatusOpen() {
        ClaimDto dto = getClaimDtoOpen();
        User user = getUser();
        user.setId(dto.getCreatorId());

        when(userRepository.findUserById(dto.getCreatorId())).thenReturn(user);
        when(userRepository.findUserById(dto.getExecutorId())).thenReturn(null);

        Claim claim = converter.convert(dto);

        assertAll(
                () -> assertEquals(dto.getId(), claim.getId()),
                () -> assertEquals(dto.getTitle(), claim.getTitle()),
                () -> assertEquals(dto.getDescription(), claim.getDescription()),
                () -> assertEquals(dto.getPlanExecuteDate(), claim.getPlanExecuteDate().toEpochMilli()),
                () -> assertEquals(dto.getCreateDate(), claim.getCreateDate().toEpochMilli()),
                () -> assertEquals(dto.getStatus(), claim.getStatus()),
                () -> assertEquals(dto.getCreatorId(), claim.getCreator().getId()),
                () -> assertEquals(dto.getExecutorId(), claim.getExecutor()),
                () -> assertNull(dto.getExecutorId()),
                () -> assertNull(claim.getExecutor()),
                () -> assertNull(dto.getFactExecuteDate()),
                () -> assertNull(claim.getFactExecuteDate())
        );
    }


    @Test
    void convertClaimRequestDtoWithStatusInProgress() {
        ClaimDto dto = getClaimDtoInProgress();
        User user = getUser();
        user.setId(dto.getCreatorId());

        when(userRepository.findUserById(any())).thenReturn(user);

        Claim claim = converter.convert(dto);

        assertAll(
                () -> assertEquals(dto.getId(), claim.getId()),
                () -> assertEquals(dto.getTitle(), claim.getTitle()),
                () -> assertEquals(dto.getDescription(), claim.getDescription()),
                () -> assertEquals(dto.getPlanExecuteDate(), claim.getPlanExecuteDate().toEpochMilli()),
                () -> assertEquals(dto.getCreateDate(), claim.getCreateDate().toEpochMilli()),
                () -> assertEquals(dto.getStatus(), claim.getStatus()),
                () -> assertEquals(dto.getCreatorId(), claim.getCreator().getId()),
                () -> assertEquals(dto.getExecutorId(), claim.getExecutor().getId()),
                () -> assertNotNull(dto.getExecutorId()),
                () -> assertNull(dto.getFactExecuteDate()),
                () -> assertNull(claim.getFactExecuteDate())

        );
    }


}






