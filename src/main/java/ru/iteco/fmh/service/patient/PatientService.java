package ru.iteco.fmh.service.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ru.iteco.fmh.dao.repository.PatientRepository;
import ru.iteco.fmh.dto.patient.PatientAdmissionDto;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.model.Patient;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientService implements IPatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ConversionServiceFactoryBean factoryBean;


    @Override
    public List<PatientAdmissionDto> getAllPatients() {
        List<Patient> patientList = patientRepository.findAll();
        ConversionService conversionService = factoryBean.getObject();
        return patientList.stream()
                .map(i -> conversionService.convert(i, PatientAdmissionDto.class))
                .collect(Collectors.toList());
    }

//    TODO: Anastasya
//    @Override
//    public List<PatientDto> getAllPatients() {
//        List<Patient> patientList = patientRepository.findAll();
//        ConversionService conversionService = factoryBean.getObject();
//        return patientList.stream()
//                .map(i -> conversionService.convert(i, PatientDto.class))
//                .collect(Collectors.toList());
//    }

//    TODO: Anastasya
//    @Override
//    public List<PatientDto> getPatientsByStatus(PatientsStatus patientsStatus) {
//        List<Patient> patientList = patientRepository.findByStatus(patientsStatus);
//        ConversionService conversionService = factoryBean.getObject();
//        return patientList.stream()
//                .map(i -> conversionService.convert(i, PatientDto.class))
//                .collect(Collectors.toList());
//    }

    @Override
    public PatientDto getPatient(Integer id) {
        Patient patient = patientRepository.getOne(id);
        ConversionService conversionService = factoryBean.getObject();
        return conversionService.convert(patient, PatientDto.class);
    }

    @Override
    public PatientDto createPatient(PatientDto dto) {
        ConversionService conversionService = factoryBean.getObject();
        Patient entity = conversionService.convert(dto, Patient.class);
        entity = patientRepository.save(entity);
        return conversionService.convert(entity, PatientDto.class);
    }

    @Override
    public PatientDto updatePatient(PatientDto dto) {
        // TODO: 27.01.2021 подумать над необходимостью метода, так как дублирует  create
        ConversionService conversionService = factoryBean.getObject();
        Patient entity = conversionService.convert(dto, Patient.class);
        entity = patientRepository.save(entity);
        return conversionService.convert(entity, PatientDto.class);
    }
}
