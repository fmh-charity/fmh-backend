package ru.iteco.fmh.converter;

import org.junit.jupiter.api.Test;
import ru.iteco.fmh.converter.claim.ClaimToClaimDtoConverter;
import ru.iteco.fmh.dao.repository.UserRepository;
import ru.iteco.fmh.dto.claim.ClaimDto;
import ru.iteco.fmh.model.task.claim.Claim;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static ru.iteco.fmh.TestUtils.getClaimInProgress;
import static ru.iteco.fmh.TestUtils.getClaimOpen;


public class ClaimToClaimDtoConverterTest {

    UserRepository userRepository = mock(UserRepository.class);

    ClaimToClaimDtoConverter converter = new ClaimToClaimDtoConverter(userRepository);

    @Test
    void convertClaimForOpen() {
        Claim claim = getClaimOpen();

        ClaimDto dto = converter.convert(claim);
        System.out.println(dto.getCreatorName());
        System.out.println(dto.getExecutorName());
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
