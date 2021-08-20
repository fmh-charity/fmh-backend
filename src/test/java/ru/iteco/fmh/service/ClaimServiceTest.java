package ru.iteco.fmh.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.converter.ClaimToClaimDtoConverterTest;
import ru.iteco.fmh.dao.repository.ClaimRepository;
import ru.iteco.fmh.dto.claim.ClaimDto;
import ru.iteco.fmh.model.task.StatusE;
import ru.iteco.fmh.model.task.claim.Claim;
import ru.iteco.fmh.service.claim.ClaimService;

import java.time.LocalDateTime;
import java.util.Optional;

import static ru.iteco.fmh.TestUtils.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static ru.iteco.fmh.model.task.StatusE.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClaimServiceTest {
    @Autowired
    ClaimService sut;

    @MockBean
    ClaimRepository claimRepository;

    @Autowired
    ConversionServiceFactoryBean factoryBean;

    @Test
    public void createClaimShouldPassSuccessExecutorNotNull() {
        // given
        Claim claim = ClaimToClaimDtoConverterTest.getClaim2();
        claim.setId(6);
        ClaimDto dto = factoryBean.getObject().convert(claim, ClaimDto.class);

        when(claimRepository.save(any())).thenReturn(claim);

        Integer resultId = sut.createClaim(dto);
        assertEquals(claim.getStatus(),IN_PROGRESS);
        assertEquals(6, resultId);
    }


        @Test
    public void getClaimShouldPassSuccess() {
        ConversionService conversionService = factoryBean.getObject();
        // given
        Claim claim = ClaimToClaimDtoConverterTest.getClaim2();
        int claimId = 1;
        when(claimRepository.findById(any())).thenReturn(Optional.of(claim));
        ClaimDto expected = conversionService.convert(claim, ClaimDto.class);
            System.out.println("Expected in test = " + expected );//executor = null
        ClaimDto result = sut.getClaim(expected.getId());
        assertEquals(expected, result);
    }

//    @Test
//    public void updateClaimShouldPassSuccess() {
//        ConversionService conversionService = factoryBean.getObject();
//
//        // claim
//        Claim claim = getClaim();
//        ClaimDto given = conversionService.convert(claim, ClaimDto.class);
//
//        when(claimRepository.save(any())).thenReturn(claim);
//
//        ClaimDto result = sut.updateClaim(given);
//
//        assertAll(
//                () -> assertEquals(given.getId(), result.getId()),
//                () -> assertEquals(given.getDescription(), result.getDescription()),
//                () -> assertEquals(given.getPlanExecuteDate(), result.getPlanExecuteDate()),
//                () -> assertEquals(given.getFactExecuteDate(), result.getFactExecuteDate()),
//                () -> assertEquals(given.getCreateDate(), result.getCreateDate()),
//                () -> assertEquals(given.getStatus(), result.getStatus()),
//                () -> assertEquals(given.getExecutor(), result.getExecutor()),
//                () -> assertEquals(given.getCreator(), result.getCreator())
//        );
//    }
//

}
