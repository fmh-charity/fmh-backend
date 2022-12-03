package ru.iteco.fmh.converter.patient;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dto.patient.PatientAdmissionDto;
import ru.iteco.fmh.model.Patient;

/**
 * конвертер из {@link Patient} в {@link PatientAdmissionDto}//для «Пациенты» (Просмотр списка пациентов)
 */
@Component
public class PatientToPatientAdmissionDtoConverter implements Converter<Patient, PatientAdmissionDto> {
    @Override
    public PatientAdmissionDto convert(Patient patient) {
        return PatientAdmissionDto.builder()
            .id(patient.getId())
            .firstName(patient.getFirstName())
            .lastName(patient.getLastName())
            .middleName(patient.getMiddleName())
            .patientStatus(patient.getStatus())
            .roomId(patient.getRoom() != null ? patient.getRoom().getId() : null)
            .birthDate(patient.getBirthDate() != null ? patient.getBirthDate().toEpochMilli() : null)
            .factDateIn(patient.getFactDateIn() != null ? patient.getFactDateIn().toEpochMilli() : null)
            .factDateOut(patient.getFactDateOut() != null ? patient.getFactDateOut().toEpochMilli() : null)
            .planDateIn(patient.getPlanDateIn() != null ? patient.getPlanDateIn().toEpochMilli() : null)
            .planDateOut(patient.getPlanDateOut() != null ? patient.getPlanDateOut().toEpochMilli() : null)
            .build();
    }
}
