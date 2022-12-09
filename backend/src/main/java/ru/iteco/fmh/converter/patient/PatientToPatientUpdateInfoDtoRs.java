package ru.iteco.fmh.converter.patient;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dto.patient.PatientUpdateInfoDtoRs;
import ru.iteco.fmh.model.Patient;

@Component
public class PatientToPatientUpdateInfoDtoRs implements Converter<Patient, PatientUpdateInfoDtoRs> {


    @Override
    public PatientUpdateInfoDtoRs convert(Patient patient) {
        return new PatientUpdateInfoDtoRs(patient.getId(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getMiddleName(),
                patient.getBirthDate());
    }
}
