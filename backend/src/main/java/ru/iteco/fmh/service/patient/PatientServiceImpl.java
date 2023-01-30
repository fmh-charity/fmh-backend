package ru.iteco.fmh.service.patient;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iteco.fmh.dao.repository.PatientRepository;
import ru.iteco.fmh.dao.repository.RoomRepository;
import ru.iteco.fmh.dto.PageDto;
import ru.iteco.fmh.dto.patient.*;
import ru.iteco.fmh.exceptions.NotFoundException;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.PatientStatus;
import ru.iteco.fmh.model.Room;
import ru.iteco.fmh.util.PatientsPageable;
import ru.iteco.fmh.util.PatientsSpecification;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final ConversionService conversionService;
    private final RoomRepository roomRepository;
    private final PatientsPageable patientPageable;
    private final PatientsSpecification patientSpecification;

    @Transactional(readOnly = true)
    @Override
    public PageDto<PatientByStatusRs> getPage(Pageable pageable, SearchPatientsDto searchPatientsDto) {
        return Optional.of(patientRepository.findAll(patientSpecification.getSpecificationPatient(searchPatientsDto), patientPageable.getPageRequest(pageable)))
                .map(page -> new PageDto<PatientByStatusRs>()
                        .setData(page.map(patient -> conversionService.convert(patient, PatientByStatusRs.class)).getContent())
                        .setTotal(page.getTotalElements())
                        .setPage(page.getNumber()))
                .orElse(null);
    }

    @Override
    public List<PatientByStatusRs> getAllPatientsByStatus(List<String> statusList) {
        List<PatientStatus> patientStatuses = statusList.stream().map(PatientStatus::valueOf).collect(Collectors.toList());
        List<Patient> patientList = patientRepository.findAllByStatusIn(patientStatuses);

        return patientList.stream()
                .map(patient -> conversionService.convert(patient, PatientByStatusRs.class))
                .collect(Collectors.toList());
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

        patient.setFirstName(patientDto.getFirstName());
        patient.setMiddleName(patientDto.getMiddleName());
        patient.setLastName(patientDto.getLastName());
        patient.setBirthDate(patientDto.getBirthDate());
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
