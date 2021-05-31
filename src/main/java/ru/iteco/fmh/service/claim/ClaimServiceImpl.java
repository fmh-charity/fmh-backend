package ru.iteco.fmh.service.claim;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ru.iteco.fmh.dao.repository.ClaimRepository;
import ru.iteco.fmh.dto.claim.ClaimDto;
import ru.iteco.fmh.dto.claim.ClaimShortInfoDto;
import ru.iteco.fmh.dto.note.NoteShortInfoDto;
import ru.iteco.fmh.model.Claim;
import ru.iteco.fmh.model.Note;
import ru.iteco.fmh.model.StatusE;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClaimServiceImpl implements ClaimService{
    private ClaimRepository claimRepository;
    private ConversionServiceFactoryBean factoryBean;

    @Autowired
    public ClaimServiceImpl(ClaimRepository claimRepository, ConversionServiceFactoryBean factoryBean) {
        this.claimRepository = claimRepository;
        this.factoryBean = factoryBean;
    }


    @Override
    public List<ClaimShortInfoDto> getAllClaims() {
        List<Claim> list = claimRepository.findAllByStatusOrderByPlanExecuteDate(StatusE.active);
        ConversionService conversionService = factoryBean.getObject();
        return list.stream()
                .map(i -> conversionService.convert(i, ClaimShortInfoDto.class))
                .collect(Collectors.toList());
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
