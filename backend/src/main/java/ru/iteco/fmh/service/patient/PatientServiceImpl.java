package ru.iteco.fmh.service.patient;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iteco.fmh.dao.repository.AdmissionRepository;
import ru.iteco.fmh.dao.repository.PatientRepository;
import ru.iteco.fmh.dto.patient.PatientAdmissionDto;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.model.Patient;

import java.util.List;
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
        patient = patientRepository.save(patient);
        return conversionService.convert(patient, PatientDto.class);
    }

    @Override
    public PatientDto getPatient(int id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Пациента с таким ID не существует"));

        return conversionService.convert(patient, PatientDto.class);
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
