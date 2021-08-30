package ru.iteco.fmh.converter.admission;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.converter.patient.fromPatientDto.PatientDtoToPatientConverter;
import ru.iteco.fmh.dto.admission.AdmissionDto;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.admission.Admission;

/**
 * конвертер из {@link AdmissionDto} в {@link Admission}
 */
@Component
@RequiredArgsConstructor
public class AdmissionDtoToAdmissionConverter implements Converter<AdmissionDto, Admission> {

    private final PatientDtoToPatientConverter patientConverter;

    @Override
    public Admission convert(@NonNull AdmissionDto dto) {
        Admission admission = new Admission();
        BeanUtils.copyProperties(dto, admission);
        Patient patient = dto.getPatient()!=null? patientConverter.convert(dto.getPatient()) : null;
        admission.setPatient(patient);
        return admission;
    }
}
