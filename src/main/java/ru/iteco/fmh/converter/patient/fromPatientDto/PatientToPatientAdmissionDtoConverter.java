package ru.iteco.fmh.converter.patient.fromPatientDto;

import org.springframework.core.convert.converter.Converter;
import ru.iteco.fmh.dto.patient.PatientAdmissionDto;
import ru.iteco.fmh.model.admission.Admission;
import ru.iteco.fmh.model.Patient;

import java.time.LocalDate;


/**
 * конвертер из {@link Patient} в {@link PatientAdmissionDto}//для «Пациенты» (Просмотр списка пациентов)
 */
public class PatientToPatientAdmissionDtoConverter implements Converter<Patient, PatientAdmissionDto>,
        IPatientToPatientAdmissionDtoConverter {

//    TODO: Anastasya, не понял что-здесь будет
//    private final Admission admission;
//
//    public PatientToPatientAdmissionDtoConverter(Admission admission) {
//        this.admission = admission;
//    }

    @Override
    public PatientAdmissionDto convert(Patient patient) {
        Admission currentAdmission = patient.getCurrentAdmission();

        return PatientAdmissionDto.builder()
                .id(patient.getId())
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .middleName(patient.getMiddleName())
                .birthday(patient.getBirthday())
                .admissionsStatus(currentAdmission.getAdmissionsStatus())
                .factDateIn(currentAdmission.getFactDateIn())
                .factDateOut(currentAdmission.getFactDateOut())
                .planDateIn(currentAdmission.getPlanDateIn())
                .planDateOut(currentAdmission.getPlanDateOut())

                // данные по-умолчанию, заполним в patientService
                .dateIn(null)
                .dateOut(null)
                .isPlanDateIn(false)
                .isPlanDateOut(false)

//                TODO: Anastasya, наверное это нужно убрать
//                .planDateIn(admission.getPlanDateIn())
//                .planDateOut(admission.getPlanDateOut())
//                .factDateIn(admission.getFactDateIn())
//                .factDateOut(admission.getFactDateOut())
                .build();
    }
}
