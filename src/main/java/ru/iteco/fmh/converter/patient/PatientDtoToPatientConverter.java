package ru.iteco.fmh.converter.patient;

import lombok.NonNull;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.model.Patient;

/**
 * конвертер из {@link PatientDto} в {@link Patient}
 */
@Component
public class PatientDtoToPatientConverter implements Converter<PatientDto, Patient> {

    @Override
    public Patient convert(@NonNull PatientDto dto) {
        Patient entity = new Patient();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }
}
