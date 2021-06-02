package ru.iteco.fmh.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.dao.repository.ClaimRepository;
import ru.iteco.fmh.dto.claim.ClaimDto;
import ru.iteco.fmh.model.Claim;
import ru.iteco.fmh.model.StatusE;
import ru.iteco.fmh.service.claim.ClaimService;
import java.time.LocalDateTime;
import java.util.Optional;

import static ru.iteco.fmh.TestUtils.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

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
    public void createClaimShouldPassSuccess() {
        ConversionService conversionService = factoryBean.getObject();
        // given
        Claim claim = getClaim();
        when(claimRepository.save(any())).thenReturn(claim);
        ClaimDto expected = conversionService.convert(claim, ClaimDto.class);
        ClaimDto result = sut.createClaim(expected);
        assertEquals(expected, result);
    }

    @Test
    public void getClaimShouldPassSuccess() {
        ConversionService conversionService = factoryBean.getObject();
        // given
        Claim claim = getClaim();
        int claimId = 1;
        when(claimRepository.findById(any())).thenReturn(Optional.of(claim));
        ClaimDto expected = conversionService.convert(claim, ClaimDto.class);
        ClaimDto result = sut.getClaim(claimId);
        assertEquals(expected, result);
    }

    private static Claim getClaim() {
        return Claim.builder()
                .id(Integer.valueOf(getNumeric(2)))
                .creator(getUser())
                .executor(getUser())
                .description(getAlphabeticStringR())
                .createDate(LocalDateTime.now())
                .planExecuteDate(LocalDateTime.now())
                .factExecuteDate(LocalDateTime.now())
                .status(StatusE.active)
                .comment(getAlphabeticStringR())
                .build();
    }
}
