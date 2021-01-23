package ru.iteco.fmh.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.iteco.fmh.dto.PatientDto;
import ru.iteco.fmh.model.Patient;

/**
 * конвертер из {@link Patient} в {@link PatientDto}
 */
@Service
public class PatientToPatientDtoConverter implements Converter<Patient, PatientDto> {

    @Override
    public PatientDto convert(Patient patient) {
        return PatientDto.builder()
                .id(patient.getId())
                .name(patient.getFirstName())
                .middleName(patient.getMiddleName())
                .lastName(patient.getLastName())
                .birthDate(patient.getBirthday())
                .build();
    }
}
