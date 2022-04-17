package ru.iteco.fmh.converter.patient;

import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.model.Patient;

/**
 * конвертер из {@link Patient} в {@link PatientDto}//для «Пациенты» ( Создание пациента, изменение пациента)
 */
@Component
public class PatientToPatientDtoConverter implements Converter<Patient, PatientDto> {

    @Override
    public PatientDto convert(Patient patient) {
        PatientDto dto = new PatientDto();
        BeanUtils.copyProperties(patient, dto);

        dto.setBirthDate(patient.getBirthDate() != null ? patient.getBirthDate().toEpochMilli() : null);
        return dto;
    }
}
