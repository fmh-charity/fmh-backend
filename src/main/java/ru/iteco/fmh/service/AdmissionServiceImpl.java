package ru.iteco.fmh.service;

import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ru.iteco.fmh.dao.repository.AdmissionRepository;
import ru.iteco.fmh.dto.AdmissionDto;
import ru.iteco.fmh.model.Admission;

/**
 * Реализация сервиса для работы с госпитализацией {@link AdmissionService}
 */
@Service
public class AdmissionServiceImpl implements AdmissionService {

    private final AdmissionRepository admissionRepository;
    private final ConversionServiceFactoryBean factoryBean;

    public AdmissionServiceImpl(AdmissionRepository admissionRepository, ConversionServiceFactoryBean factoryBean) {
        this.admissionRepository = admissionRepository;
        this.factoryBean = factoryBean;
    }

    @Override
    public AdmissionDto getAdmissionInfo(Integer id) {
        ConversionService conversionService = factoryBean.getObject();
        return admissionRepository.findById(id)
                .map(admission -> conversionService.convert(admission, AdmissionDto.class))
                .orElse(null);
    }

    @Override
    public Integer createOrUpdateAdmission(AdmissionDto admissionDto) {
        Admission admission = factoryBean.getObject().convert(admissionDto, Admission.class);
        return admissionRepository.save(admission).getId();
    }
}
