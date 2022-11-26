package ru.iteco.fmh.converter.patient;

import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dto.patient.PatientInfoDto;
import ru.iteco.fmh.model.Patient;

import java.time.LocalDate;

/**
 * конвертер из {@link Patient} в {@link PatientInfoDto}//для «Пациенты» ( Создание пациента, изменение пациента)
 */
@Component
public class PatientToPatientDtoConverter implements Converter<Patient, PatientInfoDto> {

    @Override
    public PatientInfoDto convert(Patient patient) {
        PatientInfoDto dto = new PatientInfoDto();
        BeanUtils.copyProperties(patient, dto);

        dto.setBirthDate(patient.getBirthDate() != null ? patient.getBirthDate().toEpochDay() : null);

        return dto;
    }
}
