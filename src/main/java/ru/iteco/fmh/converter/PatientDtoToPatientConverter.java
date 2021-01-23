package ru.iteco.fmh.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.iteco.fmh.dto.PatientDto;
import ru.iteco.fmh.model.Patient;

/**
 * конвертер из {@link PatientDto} в {@link Patient}
 */
@Service
public class PatientDtoToPatientConverter implements Converter<PatientDto, Patient> {

    @Override
    public Patient convert(PatientDto patientDto) {
        return Patient.builder()
                .id(patientDto.getId())
                .firstName(patientDto.getName())
                .middleName(patientDto.getMiddleName())
                .lastName(patientDto.getLastName())
                .birthday(patientDto.getBirthDate())
                .build();
    }
}
