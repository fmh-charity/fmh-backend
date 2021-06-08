package ru.iteco.fmh.service.claim;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iteco.fmh.dao.repository.ClaimRepository;
import ru.iteco.fmh.dto.claim.ClaimDto;
import ru.iteco.fmh.dto.claim.ClaimShortInfoDto;
import ru.iteco.fmh.model.Claim;
import ru.iteco.fmh.model.StatusE;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.iteco.fmh.model.StatusE.ACTIVE;

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
        List<Claim> list = claimRepository.findAllByStatusOrderByPlanExecuteDate(ACTIVE);
        ConversionService conversionService = factoryBean.getObject();
        return list.stream()
                .map(i -> conversionService.convert(i, ClaimShortInfoDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Integer createClaim(ClaimDto claimDto) {
        Claim claim = factoryBean.getObject().convert(claimDto, Claim.class);
        return claimRepository.save(claim).getId();
    }

    @Transactional
    @Override
    public ClaimDto updateClaim(ClaimDto claimDto) {
        ConversionService conversionService = factoryBean.getObject();
        Claim claim = conversionService.convert(claimDto, Claim.class);
        if (ACTIVE.equals(claim.getStatus())){
            claim = claimRepository.save(claim);
            return conversionService.convert(claim,ClaimDto.class);
        }else {
            throw new IllegalArgumentException("невозможно изменить заявку с данным статусом");
        }
    }


    @Transactional
    @Override
    public ClaimDto changeStatus(Integer claimId, StatusE status) {
        Optional<Claim> optionalClaim = claimRepository.findById(claimId);
        if (optionalClaim.isPresent()) {
            ConversionService conversionService = factoryBean.getObject();
            Claim claim = optionalClaim.get();
            if (ACTIVE.equals(claim.getStatus())) {
                status.changeStatus(claim);
                claim = claimRepository.save(claim);
                return conversionService.convert(claim, ClaimDto.class);
            }
            throw new IllegalArgumentException("Невозможно изменить статус неактивной заявки");
        }
        throw new IllegalArgumentException("Заявки с таким ID не существует");
    }

    @Override
    public ClaimDto getClaim(Integer id) {
        Optional<Claim> optionalClaim = claimRepository.findById(id);
        if (optionalClaim.isPresent()) {
            ConversionService conversionService = factoryBean.getObject();
            Claim claim = optionalClaim.get();
            return conversionService.convert(claim, ClaimDto.class);
        } else {
            throw new IllegalArgumentException("Заявки с таким ID не существует");
        }
    }
}


