package ru.iteco.fmh.service.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iteco.fmh.dao.repository.PatientRepository;
import ru.iteco.fmh.dto.admission.AdmissionDto;
import ru.iteco.fmh.dto.note.NoteDto;
import ru.iteco.fmh.dto.patient.PatientAdmissionDto;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.model.Note;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.admission.Admission;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static ru.iteco.fmh.model.StatusE.ACTIVE;

@Service
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;
    private final ConversionServiceFactoryBean factoryBean;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository, ConversionServiceFactoryBean factoryBean) {
        this.patientRepository = patientRepository;
        this.factoryBean = factoryBean;
    }

    @Override
    public List<PatientAdmissionDto> getAllPatientsByStatus(List<String> statusList) {
        List<Patient> patientList = patientRepository.findAll();
        ConversionService conversionService = factoryBean.getObject();

        return patientList.stream()
                .filter(patient -> statusList.contains(patient.getStatus().toString()))
                .map(patient -> conversionService.convert(patient, PatientAdmissionDto.class))
                .map(patientAdmissionDto -> patientAdmissionDto != null ? setAdmissionDates(patientAdmissionDto) : null)
                .collect(Collectors.toList());
    }

    @Override
    public Integer createPatient(PatientDto dto) {
        Patient entity = factoryBean.getObject().convert(dto, Patient.class);
        return patientRepository.save(entity).getId();
    }

    @Transactional
    @Override
    public PatientDto updatePatient(PatientDto patientDto) {
        ConversionService conversionService = factoryBean.getObject();
        Patient patient = conversionService.convert(patientDto, Patient.class);
        patient = patientRepository.save(patient);
        return conversionService.convert(patient, PatientDto.class);
    }

    @Override
    public PatientDto getPatient(Integer id) {
        Patient patient = patientRepository.findById(id).orElse(null);
        if (patient == null) {
            throw new IllegalArgumentException("Пациента с таким ID не существует");
        }
        ConversionService conversionService = factoryBean.getObject();
        return conversionService.convert(patient, PatientDto.class);
    }

    // ставит верные dateIn, dateOut и флаги для отправки на фронт
    private PatientAdmissionDto setAdmissionDates(PatientAdmissionDto patientAdmissionDto) {
        LocalDate factDateIn = patientAdmissionDto.getFactDateIn();
        LocalDate factDateOut = patientAdmissionDto.getFactDateOut();
        LocalDate planDateIn = patientAdmissionDto.getPlanDateIn();
        LocalDate planDateOut = patientAdmissionDto.getPlanDateOut();

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
