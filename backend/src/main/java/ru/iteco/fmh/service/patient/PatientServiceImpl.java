package ru.iteco.fmh.service.patient;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iteco.fmh.dao.repository.PatientRepository;
import ru.iteco.fmh.dao.repository.RoomRepository;
import ru.iteco.fmh.dto.patient.PatientAdmissionDto;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.Room;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final ConversionService conversionService;
    private final RoomRepository roomRepository;

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
    public PatientAdmissionDto createPatient(PatientAdmissionDto patientDto) {
        Patient patient = conversionService.convert(patientDto, Patient.class);

        patient = patientRepository.save(patient);
        return conversionService.convert(patient, PatientAdmissionDto.class);
    }

    @Transactional
    @Override
    public PatientAdmissionDto updatePatient(PatientAdmissionDto patientDto) {
        Patient patient = patientRepository.findPatientById(patientDto.getId());
        Room room = null;
        if (patientDto.getRoomId() != null) {
            room = roomRepository.findRoomById(patientDto.getRoomId());
        }
        setAdmissionDates(patientDto);

        patient.setFirstName(patientDto.getFirstName());
        patient.setMiddleName(patientDto.getMiddleName());
        patient.setLastName(patientDto.getLastName());
        patient.setBirthDate(Instant.ofEpochMilli(patientDto.getBirthDate()));
        patient.setStatus(patientDto.getPatientStatus());
        patient.setRoom(room);
        patient.setPlanDateIn(Instant.ofEpochMilli(patientDto.getPlanDateIn()));
        patient.setPlanDateOut(Instant.ofEpochMilli(patientDto.getPlanDateOut()));
        patient.setFactDateIn(Instant.ofEpochMilli(patientDto.getFactDateIn()));
        patient.setFactDateOut(Instant.ofEpochMilli(patientDto.getFactDateOut()));

        patient = patientRepository.save(patient);
        return conversionService.convert(patient, PatientAdmissionDto.class);
    }

    @Transactional
    @Override
    public PatientDto getPatient(Integer id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Пациента с таким ID не существует"));

        return getPatientDto(patient);
    }

    private PatientDto getPatientDto(Patient patient) {
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
