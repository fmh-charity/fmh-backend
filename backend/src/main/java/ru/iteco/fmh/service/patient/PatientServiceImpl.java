package ru.iteco.fmh.service.patient;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iteco.fmh.dao.repository.PatientRepository;
import ru.iteco.fmh.dto.admission.AdmissionDto;
import ru.iteco.fmh.dto.patient.PatientAdmissionDto;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.admission.AdmissionsStatus;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.iteco.fmh.model.admission.AdmissionsStatus.ACTIVE;


@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final ConversionService conversionService;

    @Override
    public PatientAdmissionDto getAllPatientsByStatus(
            List<AdmissionsStatus> status, int pages, int elements, boolean lastName) {
        Page<Patient> patientPage;

        Pageable pageableList = lastName
                ? PageRequest.of(pages, elements, Sort.by("lastName"))
                : PageRequest.of(pages, elements, Sort.by("lastName").descending());

        if (status == null || status.isEmpty()) {
            patientPage = patientRepository.findAllByStatusIn(List.of(ACTIVE), pageableList);
        } else {
            patientPage = patientRepository.findAllByStatusIn(status, pageableList);
        }

        return PatientAdmissionDto.builder()
                .pages(patientPage.getTotalPages())
                .elements(
                        patientPage.stream()
                                .map(p -> conversionService.convert(p, PatientDto.class))
                                .collect(Collectors.toList()))
                .build();
    }

    @Transactional
    @Override
    public PatientDto createPatient(PatientDto patientDto) {
        Patient patient = conversionService.convert(patientDto, Patient.class);
        patient = patientRepository.save(patient);
        return getPatientDto(patient);
    }

    @Transactional
    @Override
    public PatientDto updatePatient(@NotNull PatientDto patientDto) {
        Patient patient = patientRepository.findPatientById(patientDto.getId());

        patient.setFirstName(patientDto.getFirstName());
        patient.setMiddleName(patientDto.getMiddleName());
        patient.setLastName(patientDto.getLastName());
        patient.setBirthDate(Instant.ofEpochMilli(patientDto.getBirthDate()));

        patient = patientRepository.save(patient);
        return getPatientDto(patient);
    }

    @Transactional
    @Override
    public PatientDto getPatient(Integer id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Пациента с таким ID не существует"));

        return getPatientDto(patient);
    }

    private @NotNull PatientDto getPatientDto(Patient patient) {
        PatientDto dto = conversionService.convert(patient, PatientDto.class);
        Set<Integer> admissionIds = new HashSet<>();

        assert dto != null;
        dto.setCurrentAdmission(patient.getCurrentAdmission() == null
                ? null : conversionService.convert(patient.getCurrentAdmission(), AdmissionDto.class));

        if (patient.getAdmissions().size() > 0) {
            patient.getAdmissions().forEach(a -> admissionIds.add(a.getId()));
            dto.setCurrentAdmission(conversionService.convert(patient.getCurrentAdmission(), AdmissionDto.class));
        }

        dto.setAdmissions(admissionIds);

        return dto;
    }

    // ставит верные dateIn, dateOut и флаги для отправки на фронт
    private @NotNull PatientAdmissionDto setAdmissionDates(@NotNull PatientAdmissionDto patientAdmissionDto) {
        Long factDateIn = patientAdmissionDto.getFactDateIn();
        Long factDateOut = patientAdmissionDto.getFactDateOut();
        Long planDateIn = patientAdmissionDto.getPlanDateIn();
        Long planDateOut = patientAdmissionDto.getPlanDateOut();

        // ставим dateIn
        if (factDateIn != null) {
            patientAdmissionDto.setDateIn(factDateIn);
            patientAdmissionDto.setDateInBoolean(true);
        } else {
            patientAdmissionDto.setDateIn(planDateIn);
            patientAdmissionDto.setDateInBoolean(false);
        }

        // ставим dateOut
        if (factDateOut != null) {
            patientAdmissionDto.setDateOut(factDateOut);
            patientAdmissionDto.setDateOutBoolean(true);
        } else {
            patientAdmissionDto.setDateOut(planDateOut);
            patientAdmissionDto.setDateInBoolean(false);
        }

        return patientAdmissionDto;
    }
}
