package ru.iteco.fmh.converter;

import org.junit.jupiter.api.Test;
import ru.iteco.fmh.converter.claim.ClaimToClaimDtoConverter;
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


public class ClaimToClaimDtoConverterTest {

    UserRepository userRepository = mock(UserRepository.class);

    ClaimToClaimDtoConverter converter = new ClaimToClaimDtoConverter(userRepository);

    @Test
    void convertClaimForOpen() {
        Claim claim = getClaimOpen();
        User user = getUser();
        user.setId(3);
        when(userRepository.findUserById(any())).thenReturn(user);
        ClaimDto dto = converter.convert(claim);
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
                () -> assertNull(claim.getExecutor())
        );
    }


    @Test
    void convertClaimForInProgress() {
        Claim claim = getClaimInProgress();
        User user = getUser();
        user.setId(2);
        when(userRepository.findUserById(any())).thenReturn(user);
        ClaimDto dto = converter.convert(claim);
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
                () -> assertNotNull(claim.getExecutor())
        );
    }


}
