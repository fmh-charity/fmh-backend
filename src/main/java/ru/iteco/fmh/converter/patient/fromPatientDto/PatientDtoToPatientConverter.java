package ru.iteco.fmh.converter.patient.fromPatientDto;

import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.model.Patient;

/**
 * конвертер из {@link PatientDto} в {@link Patient}
 */
public class PatientDtoToPatientConverter implements Converter<PatientDto, Patient>, IPatientDtoToPatientConverter {

    @Override
    public Patient convert(PatientDto dto) {
        Patient entity = new Patient();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }
}
