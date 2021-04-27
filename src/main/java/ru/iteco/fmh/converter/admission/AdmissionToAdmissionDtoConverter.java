package ru.iteco.fmh.converter.admission;

import org.springframework.core.convert.converter.Converter;
import ru.iteco.fmh.converter.patient.fromPatient.IPatientToPatientDtoConverter;
import ru.iteco.fmh.dto.admission.AdmissionDto;
import ru.iteco.fmh.model.admission.Admission;


/**
 * конвертер из {@link Admission} в {@link AdmissionDto}
 */
public class AdmissionToAdmissionDtoConverter implements Converter<Admission, AdmissionDto> {

    private IPatientToPatientDtoConverter patientConverter;

    public AdmissionToAdmissionDtoConverter(IPatientToPatientDtoConverter patientConverter) {
        this.patientConverter = patientConverter;
    }

    @Override
    public AdmissionDto convert(Admission admission) {
        return AdmissionDto.builder()
                .id(admission.getId())
                .patient(patientConverter.convert(admission.getPatient()))
                .planDateIn(admission.getPlanDateIn())
                .planDateOut(admission.getPlanDateOut())
                .factDateIn(admission.getFactDateIn())
                .factDateOut(admission.getFactDateOut())
                .comment(admission.getComment())
                .build();
    }
}
