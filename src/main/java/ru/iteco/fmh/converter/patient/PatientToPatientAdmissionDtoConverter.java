package ru.iteco.fmh.converter.patient;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dto.patient.PatientAdmissionDto;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.admission.Admission;

/**
 * конвертер из {@link Patient} в {@link PatientAdmissionDto}//для «Пациенты» (Просмотр списка пациентов)
 */
@Component
public class PatientToPatientAdmissionDtoConverter implements Converter<Patient, PatientAdmissionDto> {

    @Override
    public PatientAdmissionDto convert(Patient patient) {
        Admission currentAdmission = patient.getCurrentAdmission();
        PatientAdmissionDto dto = PatientAdmissionDto.builder()
                .id(patient.getId())
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .middleName(patient.getMiddleName())
                .admissionsStatus(patient.getStatus())
                .build();
        dto.setBirthday(patient.getBirthDate() != null ? patient.getBirthDate().toEpochMilli() : null);
        if (currentAdmission != null) {
            dto.setFactDateIn(currentAdmission.getFactDateIn() != null ? currentAdmission.getFactDateIn().toEpochMilli() : null);
            dto.setFactDateOut(currentAdmission.getFactDateOut() != null ? currentAdmission.getFactDateOut().toEpochMilli() : null);
            dto.setPlanDateIn(currentAdmission.getPlanDateIn() != null ? currentAdmission.getPlanDateIn().toEpochMilli() : null);
            dto.setPlanDateOut(currentAdmission.getPlanDateOut() != null ? currentAdmission.getPlanDateOut().toEpochMilli() : null);

        } else {
            dto.setFactDateIn(null);
            dto.setFactDateOut(null);
            dto.setPlanDateIn(null);
            dto.setPlanDateOut(null);

        }

        return dto;
    }
}
