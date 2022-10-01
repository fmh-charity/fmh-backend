package ru.iteco.fmh.service.admission;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iteco.fmh.dao.repository.AdmissionRepository;
import ru.iteco.fmh.dao.repository.PatientRepository;
import ru.iteco.fmh.dto.admission.AdmissionDto;
import ru.iteco.fmh.model.admission.Admission;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация сервиса для работы с госпитализацией {@link AdmissionService}
 */
@Service
@RequiredArgsConstructor
public class AdmissionServiceImpl implements AdmissionService {

    private final AdmissionRepository admissionRepository;
    private final PatientRepository patientRepository;
    private final ConversionService conversionService;

    @Override
    public List<AdmissionDto> getPatientAdmissions(int patientId) {
        return admissionRepository.findAllByPatient_IdAndDeletedIsFalse(patientId).stream()
                .map(admission -> conversionService.convert(admission, AdmissionDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public AdmissionDto getAdmission(int admissionId) {
        return admissionRepository.findById(admissionId)
                .map(admission -> conversionService.convert(admission, AdmissionDto.class))
                .orElse(null);
    }

    @Transactional
    @Override
    public AdmissionDto createOrUpdateAdmission(AdmissionDto admissionDto) {
        Admission admission = conversionService.convert(admissionDto, Admission.class);

        if (admissionDto.getId() != 0) {
            if (!admissionDto.getPatientId().equals(admissionRepository.findById(
                    admissionDto.getId()).orElseThrow().getPatient().getId())) {
                return null;
            }
        }

        admission = admissionRepository.save(admission);
        return conversionService.convert(admission, AdmissionDto.class);
    }
}
