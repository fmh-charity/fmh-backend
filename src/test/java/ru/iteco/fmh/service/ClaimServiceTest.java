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
import ru.iteco.fmh.model.task.claim.Claim;
import ru.iteco.fmh.service.claim.ClaimService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static ru.iteco.fmh.TestUtils.getClaimInProgress;
import static ru.iteco.fmh.TestUtils.getClaimOpen;
import static ru.iteco.fmh.model.task.StatusE.IN_PROGRESS;

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
//        claim.setId(6);
//        claim.getCreator().setId(5);
//        claim.getExecutor().setId(5);

        ClaimDto dto = conversionService.convert(claim, ClaimDto.class);

        when(claimRepository.save(any())).thenReturn(claim);
        ClaimDto result = sut.createClaim(dto);

        assertEquals(claim.getStatus(), IN_PROGRESS);
        assertAll(
                () -> assertEquals(dto.getId(), result.getId()),
                () -> assertEquals(dto.getDescription(), result.getDescription()),
                () -> assertEquals(dto.getPlanExecuteDate(), result.getPlanExecuteDate()),
                () -> assertEquals(dto.getFactExecuteDate(), result.getFactExecuteDate()),
                () -> assertEquals(dto.getCreateDate(), result.getCreateDate()),
                () -> assertEquals(dto.getStatus(), result.getStatus()),
                () -> assertEquals(dto.getExecutorId(), result.getExecutorId()),
                () -> assertEquals(dto.getCreatorId(), result.getCreatorId())
        );

        assertNotNull(dto.getExecutorId());
        assertNotNull(dto.getCreatorId());
    }

    @Test
    public void createClaimShouldPassSuccessExecutorNull() {
        // given
        Claim claim = getClaimOpen();
//        claim.setId(7);
//        claim.getCreator().setId(5);
        ClaimDto dto = conversionService.convert(claim, ClaimDto.class);
        when(claimRepository.save(any())).thenReturn(claim);
        ClaimDto result = sut.createClaim(dto);
        assertNull(dto.getExecutorId());
        assertEquals(claim.getStatus(), dto.getStatus());
        assertAll(
                () -> assertEquals(dto.getId(), result.getId()),
                () -> assertEquals(dto.getDescription(), result.getDescription()),
                () -> assertEquals(dto.getPlanExecuteDate(), result.getPlanExecuteDate()),
                () -> assertEquals(dto.getFactExecuteDate(), result.getFactExecuteDate()),
                () -> assertEquals(dto.getCreateDate(), result.getCreateDate()),
                () -> assertEquals(dto.getStatus(), result.getStatus()),
                () -> assertEquals(dto.getExecutorId(), result.getExecutorId()),
                () -> assertEquals(dto.getCreatorId(), result.getCreatorId())
        );

    }


    @Test
    public void getClaimShouldPassSuccess() {
        // given
        Claim claim = getClaimInProgress();
        when(claimRepository.findById(any())).thenReturn(Optional.of(claim));
        ClaimDto expected = conversionService.convert(claim, ClaimDto.class);
        ClaimDto result = sut.getClaim(expected.getId());
        assertEquals(expected, result);
    }

    @Test
    public void updateClaimShouldPassSuccess() {
        // claim
        Claim claim = getClaimInProgress();
        ClaimDto given = conversionService.convert(claim, ClaimDto.class);
        when(claimRepository.save(any())).thenReturn(claim);
        ClaimDto result = sut.updateClaim(given);
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
        ClaimDto given = conversionService.convert(claim, ClaimDto.class);

        when(claimRepository.save(any())).thenReturn(claim);

        ClaimDto result = sut.updateClaim(given);

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
