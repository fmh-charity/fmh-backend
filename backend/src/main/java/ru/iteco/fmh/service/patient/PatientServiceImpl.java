package ru.iteco.fmh.service.patient;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iteco.fmh.dao.repository.PatientRepository;
import ru.iteco.fmh.dao.repository.RoomRepository;
import ru.iteco.fmh.dto.patient.PatientByStatusRs;
import ru.iteco.fmh.dto.patient.PatientCreateInfoDtoRq;
import ru.iteco.fmh.dto.patient.PatientCreateInfoDtoRs;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.dto.patient.PatientUpdateInfoDtoRq;
import ru.iteco.fmh.dto.patient.PatientUpdateInfoDtoRs;
import ru.iteco.fmh.exceptions.NotFoundException;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.Room;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final ConversionService conversionService;
    private final RoomRepository roomRepository;

    @Override
    @Transactional
    public List<PatientByStatusRs> getAllPatientsByStatus(PageRequest pageRequest, String search) {
        return patientRepository.findAllByParameters(pageRequest, search).stream()
                .map(i -> conversionService.convert(i, PatientByStatusRs.class)).collect(Collectors.toList());
    }

    @Override
    public PatientCreateInfoDtoRs createPatient(PatientCreateInfoDtoRq patientCreateInfoDtoRq) {
        Patient patient = conversionService.convert(patientCreateInfoDtoRq, Patient.class);

        patient = patientRepository.save(patient);
        return conversionService.convert(patient, PatientCreateInfoDtoRs.class);
    }

    @Transactional
    @Override
    public PatientUpdateInfoDtoRs updatePatient(int id, PatientUpdateInfoDtoRq patientDto) {
        Patient patient = patientRepository.findPatientById(id);

        patient.getProfile().setFirstName(patientDto.getFirstName());
        patient.getProfile().setMiddleName(patientDto.getMiddleName());
        patient.getProfile().setLastName(patientDto.getLastName());
        patient.getProfile().setDateOfBirth(patientDto.getBirthDate());

        patient.setStatus(patientDto.getStatus());
        if (patientDto.getRoomId() != null) {
            Room room = roomRepository.getReferenceById(patientDto.getRoomId());
            patient.setRoom(room);
        }
        if (patientDto.isDateInBoolean()) {
            patient.setFactDateIn(patientDto.getDateIn());
        } else {
            patient.setPlanDateIn(patientDto.getDateIn());
            patient.setFactDateIn(null);
        }

        if (patientDto.isDateOutBoolean()) {
            patient.setFactDateOut(patientDto.getDateOut());
        } else {
            patient.setPlanDateOut(patientDto.getDateOut());
            patient.setFactDateOut(null);
        }

        patient = patientRepository.save(patient);
        return conversionService.convert(patient, PatientUpdateInfoDtoRs.class);

    }

    @Transactional
    @Override
    public PatientDto getPatient(Integer id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Активный пациент с данным ID отсутствует"));

        return conversionService.convert(patient, PatientDto.class);
    }

    @Override
    public void deletePatient(int id) {
        patientRepository.deleteById(id);
    }
}
