package ru.iteco.fmh.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.iteco.fmh.dto.AdmissionDto;
import ru.iteco.fmh.model.Admission;

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
                .dateIn(admission.getDateFrom())
                .dateOut(admission.getDateTo())
                .factIn(admission.getFactIn())
                .factOut(admission.getFactOut())
                .comment(admission.getComment())
                .build();
    }
}
