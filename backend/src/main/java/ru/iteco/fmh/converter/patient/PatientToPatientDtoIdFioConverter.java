package ru.iteco.fmh.converter.patient;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dto.patient.PatientDtoIdFio;
import ru.iteco.fmh.model.Patient;

/**
 * конвертер из {@link Patient} в {@link PatientDtoIdFio}//для «Пациенты» (Для wishDto)
 */
@Component
public class PatientToPatientDtoIdFioConverter implements Converter<Patient, PatientDtoIdFio> {

    @Override
    public PatientDtoIdFio convert(Patient patient) {
        return new PatientDtoIdFio(
                patient.getId(),
                patient.getFirstName(),
                patient.getMiddleName(),
                patient.getLastName()
        );
    }
}
