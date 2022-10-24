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

        Set<Admission> admissions = patient.getAdmissions();
        if (!admissions.contains(admission)) {
            admissions.add(admissionRepository.save(admission));
        }

        patient.setCurrentAdmission(getCurrentAdmission(admissions));
        patient.setAdmissions(admissions);

        patientRepository.save(patient);

        return conversionService.convert(admission, AdmissionDto.class);
    }

    @Transactional
    @Override
    public boolean deleteAdmissionById(Integer id) {
        try {
            Admission admission = admissionRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("Госпитализации с данным " + id + " не найдено"));
            Patient patient = admission.getPatient();

            admission.setDeleted(true);
            admissionRepository.save(admission);

            if (patient.getCurrentAdmission() != null && admission.equals(patient.getCurrentAdmission())) {
                patient.setCurrentAdmission(getCurrentAdmission(patient.getAdmissions()));
                patientRepository.save(patient);
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    private Admission getCurrentAdmission(Set<Admission> admissions) {
        if (admissions.size() == 1) {
            return admissions.stream().filter(a -> !a.isDeleted()).findFirst().orElse(null);
        }

        List<Admission> result = admissions.stream()
                .filter(a -> !a.isDeleted())
                .sorted(Comparator.comparing(Admission::getStatus).reversed())
                .toList();

        switch (result.get(0).getStatus()) {
            case DISCHARGED -> result = result.stream().filter(a -> a.getStatus().equals(AdmissionsStatus.DISCHARGED))
                    .sorted(Comparator.comparing(Admission::getPlanDateOut)).collect(Collectors.toList());
            default -> result = result.stream().filter(a -> a.getStatus().equals(AdmissionsStatus.ACTIVE))
                    .sorted(Comparator.comparing(Admission::getPlanDateIn)).collect(Collectors.toList());
        }

        result.forEach(System.out::println);

        return result.get(0);
    }
}
