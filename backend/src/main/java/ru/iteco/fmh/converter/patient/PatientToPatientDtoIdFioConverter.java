package ru.iteco.fmh.converter.patient;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dto.patient.PatientDtoIdFio;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.user.Profile;

/**
 * конвертер из {@link Patient} в {@link PatientDtoIdFio}//для «Пациенты» (Для wishDto)
 */
@Component
public class PatientToPatientDtoIdFioConverter implements Converter<Patient, PatientDtoIdFio> {

    @Override
    public PatientDtoIdFio convert(Patient patient) {
        Profile profile = patient.getProfile();

        return new PatientDtoIdFio(
                patient.getId(),
                profile.getFirstName(),
                profile.getMiddleName(),
                profile.getLastName()
        );
    }
}
