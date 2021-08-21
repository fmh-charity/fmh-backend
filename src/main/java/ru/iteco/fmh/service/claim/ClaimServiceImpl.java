package ru.iteco.fmh.service.claim;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iteco.fmh.dao.repository.ClaimRepository;
import ru.iteco.fmh.dto.claim.ClaimDto;
import ru.iteco.fmh.model.task.claim.Claim;
import ru.iteco.fmh.model.task.StatusE;

import java.util.List;
import java.util.stream.Collectors;

import static ru.iteco.fmh.model.task.StatusE.IN_PROGRESS;
import static ru.iteco.fmh.model.task.StatusE.OPEN;

@Service
public class ClaimServiceImpl implements ClaimService {
    private ClaimRepository claimRepository;
    private ConversionServiceFactoryBean factoryBean;


    @Autowired
    public ClaimServiceImpl(ClaimRepository claimRepository, ConversionServiceFactoryBean factoryBean) {
        this.claimRepository = claimRepository;
        this.factoryBean = factoryBean;
    }


    @Override
    public List<ClaimDto> getAllClaims() {
        List<Claim> list = claimRepository.findAllByStatusInOrderByPlanExecuteDateAscCreateDateAsc(List.of(OPEN, IN_PROGRESS));
        ConversionService conversionService = factoryBean.getObject();
        return list.stream()
                .map(i -> conversionService.convert(i, ClaimDto.class))
                .collect(Collectors.toList());
    }


    @Override
    public Integer createClaim(ClaimDto claimDto) {
        claimDto.setStatus(claimDto.getExecutor() == null ? OPEN : IN_PROGRESS);
        Claim claim = factoryBean.getObject().convert(claimDto, Claim.class);
        return claimRepository.save(claim).getId();
    }

    @Override
    public ClaimDto getClaim(Integer id) {
        Claim claim = claimRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Заявки с таким ID не существует"));
        ConversionService conversionService = factoryBean.getObject();
        return conversionService.convert(claim, ClaimDto.class);
    }

    @Transactional
    @Override
    public ClaimDto updateClaim(ClaimDto claimDto) {
        ConversionService conversionService = factoryBean.getObject();
        if (claimDto.getStatus()==StatusE.EXECUTED || claimDto.getStatus()==StatusE.CANCELLED){
            throw new IllegalArgumentException("Нельзя редактировать заявку с данным статусом");
        }
        claimDto.setStatus(claimDto.getExecutor() == null ? OPEN : IN_PROGRESS);
        Claim claim = conversionService.convert(claimDto, Claim.class);
        claim = claimRepository.save(claim);
        return conversionService.convert(claim, ClaimDto.class);
    }


    @Transactional
    @Override
    public ClaimDto changeStatus(Integer claimId, StatusE status) {
        Claim claim = claimRepository.findById(claimId).orElseThrow(() -> new IllegalArgumentException("Заявки с таким ID не существует"));
        claim.changeStatus(status);
        claim = claimRepository.save(claim);
        ConversionService conversionService = factoryBean.getObject();

        claim = claimRepository.save(claim);
        return conversionService.convert(claim, ClaimDto.class);
    }


}
//метод создания claim 2021/08/20 (разговор с Маратом)
//    @Override
//    public Integer createClaim(ClaimDto claimDto) {
//        if (claimDto.getExecutor() == null){
//            claimDto.setStatus(OPEN);
//        }else {
//            claimDto.setStatus(IN_PROGRESS);
//        }
//        Claim claim = factoryBean.getObject().convert(claimDto, Claim.class);
//        return claimRepository.save(claim).getId();
//    }


