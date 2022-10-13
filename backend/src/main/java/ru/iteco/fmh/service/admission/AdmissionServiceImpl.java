package ru.iteco.fmh.service.admission;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iteco.fmh.dao.repository.AdmissionRepository;
import ru.iteco.fmh.dao.repository.PatientRepository;
import ru.iteco.fmh.dto.admission.AdmissionDto;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.admission.Admission;
import ru.iteco.fmh.model.admission.AdmissionsStatus;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
        Patient patient = patientRepository.findPatientById(admissionDto.getPatientId());

        admission = admissionRepository.save(admission);
        Set<Admission> admissions = admissionRepository.findAdmissionsByPatientId(admissionDto.getPatientId());

        patient.setCurrentAdmission(getCurrentAdmission(admissions));
        patient.setAdmissions(admissions);

        patientRepository.save(patient);

        return conversionService.convert(admission, AdmissionDto.class);
    }

    @Override
    public boolean deleteAdmissionById(Integer id) {
        try {
            admissionRepository.deleteById(id);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    private Admission getCurrentAdmission(Set<Admission> admissions) {

        if (admissions.size() == 1) {
            return admissions.stream().findAny().orElseThrow();
        }

        List<Admission> result = admissions.stream()
                .filter(a -> !a.getStatus().equals(AdmissionsStatus.DISCHARGED))
                .sorted(Comparator.comparing(Admission::getPlanDateIn))
                .toList();

        if (result.size() < 1) {
            return admissions.stream().findAny().orElseThrow();
        }

        return result.get(result.size() - 1);
    }
}
