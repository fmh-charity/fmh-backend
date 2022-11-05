package ru.iteco.fmh.service.patient;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iteco.fmh.dao.repository.AdmissionRepository;
import ru.iteco.fmh.dao.repository.PatientRepository;
import ru.iteco.fmh.dto.admission.AdmissionDto;
import ru.iteco.fmh.dto.patient.PatientAdmissionDto;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.admission.Admission;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final AdmissionRepository admissionRepository;
    private final ConversionService conversionService;

    @Override
    public List<PatientAdmissionDto> getAllPatientsByStatus(List<String> statusList) {
        List<Patient> patientList = patientRepository.findAll();

        return patientList.stream()
                .filter(patient -> statusList.contains(patient.getStatus().toString()))
                .map(patient -> conversionService.convert(patient, PatientAdmissionDto.class))
                .map(patientAdmissionDto -> patientAdmissionDto != null ? setAdmissionDates(patientAdmissionDto) : null)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public PatientDto createOrUpdatePatient(PatientDto patientDto) {
        Patient patient = conversionService.convert(patientDto, Patient.class);

        patient.setCurrentAdmission(patientDto.getCurrentAdmission() == null
                ? null : conversionService.convert(patientDto.getCurrentAdmission(), Admission.class));
        patient.setAdmissions(patientDto.getAdmissions() != null && patientDto.getAdmissions().size() != 0
                ? admissionRepository.findAdmissionsByPatientId(patientDto.getId()) : new HashSet<>());

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

    private PatientDto getPatientDto(Patient patient) {
        PatientDto dto = conversionService.convert(patient, PatientDto.class);
        Set<Integer> admissionIds = new HashSet<>();

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
    private PatientAdmissionDto setAdmissionDates(PatientAdmissionDto patientAdmissionDto) {
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
