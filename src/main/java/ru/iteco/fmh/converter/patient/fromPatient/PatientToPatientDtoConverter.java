package ru.iteco.fmh.converter.patient.fromPatient;

import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.model.Patient;

/**
 * конвертер из {@link Patient} в {@link PatientDto}//для «Пациенты» ( Создание пациента, изменение пациента)
 */
public class PatientToPatientDtoConverter implements Converter<Patient, PatientDto>, IPatientToPatientDtoConverter {
    @Override
    public PatientDto convert(Patient patient) {
        PatientDto dto = new PatientDto();
        BeanUtils.copyProperties(patient, dto);
        return dto;
    }
}
