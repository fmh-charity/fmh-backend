package ru.iteco.fmh.service.admission;

import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ru.iteco.fmh.dao.repository.AdmissionRepository;
import ru.iteco.fmh.dto.admission.AdmissionDto;
import ru.iteco.fmh.dto.admission.AdmissionShortInfoDto;
import ru.iteco.fmh.model.admission.Admission;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<AdmissionDto> getPatientAdmissions(Integer patientId) {
        ConversionService conversionService = factoryBean.getObject();
        return admissionRepository.findAllByPatient_Id(patientId).stream()
                .map(admission -> conversionService.convert(admission, AdmissionDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public AdmissionDto getAdmission(Integer admissionId) {
        ConversionService conversionService = factoryBean.getObject();
        return admissionRepository.findById(admissionId)
                .map(admission -> conversionService.convert(admission, AdmissionDto.class))
                .orElse(null);
    }

    @Override
    public Integer createOrUpdateAdmission(AdmissionDto admissionDto) {
        Admission admission = factoryBean.getObject().convert(admissionDto, Admission.class);
        return admissionRepository.save(admission).getId();
    }
}
