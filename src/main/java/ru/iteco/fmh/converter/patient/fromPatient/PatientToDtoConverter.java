//package ru.iteco.fmh.converter.patient.fromPatient;
//
//import org.springframework.beans.BeanUtils;
//import org.springframework.core.convert.converter.Converter;
//import ru.iteco.fmh.dto.patient.PatientDto;
//import ru.iteco.fmh.model.Patient;
//
//public class PatientToDtoConverter implements Converter<Patient, PatientDto>, IPatientToDtoConverter {
//    @Override
//    public PatientDto convert(Patient patient) {
//        PatientDto dto = new PatientDto();
//        BeanUtils.copyProperties(patient, dto);
//        return dto;
//    }
//}
