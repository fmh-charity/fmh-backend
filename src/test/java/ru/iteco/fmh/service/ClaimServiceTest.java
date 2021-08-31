package ru.iteco.fmh.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.dao.repository.ClaimRepository;
import ru.iteco.fmh.dto.claim.ClaimDto;

import ru.iteco.fmh.dto.claim.ClaimRequestDto;
import ru.iteco.fmh.model.task.claim.Claim;
import ru.iteco.fmh.service.claim.ClaimService;

import java.util.Optional;


import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static ru.iteco.fmh.TestUtils.getClaimInProgress;
import static ru.iteco.fmh.TestUtils.getClaimOpen;
import static ru.iteco.fmh.model.task.StatusE.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClaimServiceTest {
    @Autowired
    ClaimService sut;

    @MockBean
    ClaimRepository claimRepository;

    @Autowired
    ConversionService conversionService;

    @Test
    public void createClaimShouldPassSuccessExecutorNotNull() {
        // given
        Claim claim = getClaimInProgress();
        claim.setId(6);
        ClaimRequestDto dto = conversionService.convert(claim, ClaimRequestDto.class);
        when(claimRepository.save(any())).thenReturn(claim);
        Integer resultId = sut.createClaim(dto);
        assertEquals(claim.getStatus(), IN_PROGRESS);
        assertEquals(6, resultId);
        assertNotNull(dto.getExecutorId());
        assertNotNull( dto.getCreatorId());
    }
    @Test
    public void createClaimShouldPassSuccessExecutorNull() {
        // given
        Claim claim = getClaimOpen();
        claim.setId(7);
        ClaimRequestDto dto = conversionService.convert(claim, ClaimRequestDto.class);
        when(claimRepository.save(any())).thenReturn(claim);
        Integer resultId = sut.createClaim(dto);
        assertNull(dto.getExecutorId());
        assertEquals(claim.getStatus(), dto.getStatus());
        assertEquals(7, resultId);
    }


    @Test
    public void getClaimShouldPassSuccess() {
        // given
        Claim claim = getClaimInProgress();
        int claimId = 1;
        when(claimRepository.findById(any())).thenReturn(Optional.of(claim));
        ClaimDto expected = conversionService.convert(claim, ClaimDto.class);
        ClaimDto result = sut.getClaim(expected.getId());
        assertEquals(expected, result);
    }

    @Test
    public void updateClaimShouldPassSuccess() {
        // claim
        Claim claim = getClaimInProgress();
        ClaimRequestDto given = conversionService.convert(claim, ClaimRequestDto.class);

        when(claimRepository.save(any())).thenReturn(claim);

        ClaimRequestDto result = sut.updateClaim(given);

        assertAll(
                () -> assertEquals(given.getId(), result.getId()),
                () -> assertEquals(given.getDescription(), result.getDescription()),
                () -> assertEquals(given.getPlanExecuteDate(), result.getPlanExecuteDate()),
                () -> assertEquals(given.getFactExecuteDate(), result.getFactExecuteDate()),
                () -> assertEquals(given.getCreateDate(), result.getCreateDate()),
                () -> assertEquals(given.getStatus(), result.getStatus()),
                () -> assertEquals(given.getExecutorId(), result.getExecutorId()),
                () -> assertEquals(given.getCreatorId(), result.getCreatorId())
        );
    }


    @Test
    public void updateClaimShouldPassSuccessNull() {
        // claim
        Claim claim = getClaimOpen();
        ClaimRequestDto given = conversionService.convert(claim, ClaimRequestDto.class);

        when(claimRepository.save(any())).thenReturn(claim);

        ClaimRequestDto result = sut.updateClaim(given);

        assertAll(
                () -> assertEquals(given.getId(), result.getId()),
                () -> assertEquals(given.getDescription(), result.getDescription()),
                () -> assertEquals(given.getPlanExecuteDate(), result.getPlanExecuteDate()),
                () -> assertEquals(given.getFactExecuteDate(), result.getFactExecuteDate()),
                () -> assertEquals(given.getCreateDate(), result.getCreateDate()),
                () -> assertEquals(given.getStatus(), result.getStatus()),
                () -> assertEquals(given.getExecutorId(), result.getExecutorId()),
                () -> assertEquals(given.getCreatorId(), result.getCreatorId())
        );
    }


}
