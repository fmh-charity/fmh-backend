package ru.iteco.fmh.converter.patient.fromPatientDto;

import org.springframework.core.convert.converter.Converter;
import ru.iteco.fmh.dto.patient.PatientAdmissionDto;
import ru.iteco.fmh.model.admission.Admission;
import ru.iteco.fmh.model.Patient;

/**
 * конвертер из {@link Patient} в {@link PatientAdmissionDto}//для «Пациенты» (Просмотр списка пациентов)
 */
public class PatientToPatientAdmissionDtoConverter implements Converter<Patient, PatientAdmissionDto>,
        IPatientToPatientAdmissionDtoConverter {

    @Override
    public PatientAdmissionDto convert(Patient patient) {
        Admission currentAdmission = patient.getCurrentAdmission();

        return PatientAdmissionDto.builder()
                .id(patient.getId())
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .middleName(patient.getMiddleName())
                .birthday(patient.getBirthDate())
                .admissionsStatus(patient.getStatus())
                .factDateIn(currentAdmission != null ? currentAdmission.getFactDateIn() : null)
                .factDateOut(currentAdmission != null ? currentAdmission.getFactDateOut() : null)
                .planDateIn(currentAdmission != null ? currentAdmission.getPlanDateIn() : null)
                .planDateOut(currentAdmission != null ? currentAdmission.getPlanDateOut() : null)
                .build();
    }
}
