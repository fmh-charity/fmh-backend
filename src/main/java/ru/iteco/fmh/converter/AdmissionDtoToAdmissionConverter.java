package ru.iteco.fmh.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.iteco.fmh.dto.AdmissionDto;
import ru.iteco.fmh.model.Admission;

/**
 * конвертер из {@link AdmissionDto} в {@link Admission}
 */
public class AdmissionDtoToAdmissionConverter implements Converter<AdmissionDto, Admission> {

    private IPatientDtoToPatientConverter patientConverter;

    public AdmissionDtoToAdmissionConverter(IPatientDtoToPatientConverter patientConverter) {
        this.patientConverter = patientConverter;
    }

    @Override
    public Admission convert(AdmissionDto admissionDto) {
        return Admission.builder()
                .id(admissionDto.getId())
                .patient(patientConverter.convert(admissionDto.getPatient()))
                .dateFrom(admissionDto.getDateIn())
                .dateTo(admissionDto.getDateOut())
                .factIn(admissionDto.getFactIn())
                .factOut(admissionDto.getFactOut())
                .comment(admissionDto.getComment())
                .build();
    }
}
