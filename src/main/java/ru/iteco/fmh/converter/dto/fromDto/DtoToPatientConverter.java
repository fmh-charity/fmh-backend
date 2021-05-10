//package ru.iteco.fmh.converter.dto.fromDto;
//
//import org.springframework.beans.BeanUtils;
//import org.springframework.core.convert.converter.Converter;
//import ru.iteco.fmh.dto.patient.PatientDto;
//import ru.iteco.fmh.model.Patient;
//
//public class DtoToPatientConverter implements Converter<PatientDto, Patient>, IDtoToPatientConverter {
//
//    @Override
//    public Patient convert(PatientDto dto) {
//        Patient entity = new Patient();
//        BeanUtils.copyProperties(dto, entity);
//        return entity;
//    }
//
//    public DtoToPatientConverter() {
//    }
//}
