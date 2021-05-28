package ru.iteco.fmh.service.claim;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ru.iteco.fmh.dao.repository.ClaimRepository;
import ru.iteco.fmh.dto.claim.ClaimDto;
import ru.iteco.fmh.model.Claim;

import java.util.List;
import java.util.Optional;

@Service
public class ClaimServiceImpl implements ClaimService{
    private ClaimRepository claimRepository;
    private ConversionServiceFactoryBean factoryBean;

    @Autowired
    public ClaimServiceImpl(ClaimRepository claimRepository, ConversionServiceFactoryBean factoryBean) {
        this.claimRepository = claimRepository;
        this.factoryBean = factoryBean;
    }

    // TODO: 28.05 Nastya
    @Override
    public List<ClaimDto> getClaims() {
        return null;
    }

    @Override
    public ClaimDto createClaim(ClaimDto claimDto) {
        ConversionService conversionService = factoryBean.getObject();
        Claim claim = conversionService.convert(claimDto, Claim.class);
        claim = claimRepository.save(claim);
        return conversionService.convert(claim, ClaimDto.class);
    }

    @Override
    public ClaimDto getClaim(Integer id) {
        Optional<Claim> optionalClaim = claimRepository.findById(id);
        if (optionalClaim.isPresent()) {
            ConversionService conversionService = factoryBean.getObject();
            Claim claim = optionalClaim.get();
            return conversionService.convert(claim, ClaimDto.class);
        } else {
            return null;
        }
    }
}
