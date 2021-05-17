package ru.iteco.fmh.service.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ru.iteco.fmh.dao.repository.PatientRepository;
import ru.iteco.fmh.dto.patient.PatientAdmissionDto;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.model.Patient;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
    public PatientDto createOrUpdatePatient(PatientDto dto) {
        ConversionService conversionService = factoryBean.getObject();
        Patient entity = conversionService.convert(dto, Patient.class);
        entity = patientRepository.save(entity);
        return conversionService.convert(entity, PatientDto.class);
    }

    @Override
    public PatientDto getPatient(Integer id) {
        Patient patient = patientRepository.findById(id).orElse(null);
        if (patient == null) {
            return null;
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
