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

    private final Admission admission;

    public PatientToPatientAdmissionDtoConverter(Admission admission) {
        this.admission = admission;
    }

    @Override
    public PatientAdmissionDto convert(Patient patient) {

        return PatientAdmissionDto.builder()
                .id(patient.getId())
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .middleName(patient.getMiddleName())
                .birthday(patient.getBirthday())
                .planDateIn(admission.getPlanDateIn())
                .planDateOut(admission.getPlanDateOut())
                .factDateIn(admission.getFactDateIn())
                .factDateOut(admission.getFactDateOut())
                .build();
    }
}
