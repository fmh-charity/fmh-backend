package ru.iteco.fmh.converter.patient;

import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dto.patient.PatientInfoDto;
import ru.iteco.fmh.model.Patient;

/**
 * конвертер из {@link Patient} в {@link PatientInfoDto}//для «Пациенты» ( Создание пациента, изменение пациента)
 */
@Component
public class PatientToPatientInfoDtoConverter implements Converter<Patient, PatientInfoDto> {

    @Override
    public PatientInfoDto convert(Patient patient) {
        PatientInfoDto dto = new PatientInfoDto();
        BeanUtils.copyProperties(patient, dto);

        return dto;
    }
}