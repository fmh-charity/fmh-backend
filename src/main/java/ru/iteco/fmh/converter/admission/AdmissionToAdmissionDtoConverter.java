package ru.iteco.fmh.converter.admission;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.converter.patient.fromPatient.PatientToPatientDtoConverter;
import ru.iteco.fmh.dto.admission.AdmissionDto;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.model.admission.Admission;

/**
 * конвертер из {@link Admission} в {@link AdmissionDto}
 */
@Component
@RequiredArgsConstructor
public class AdmissionToAdmissionDtoConverter implements Converter<Admission, AdmissionDto> {

    private final PatientToPatientDtoConverter patientConverter;

    @Override
    public AdmissionDto convert(@NonNull Admission admission) {
        AdmissionDto admissionDto = new AdmissionDto();
        BeanUtils.copyProperties(admission, admissionDto);
        PatientDto patientDto = patientConverter.convert(admission.getPatient());
        admissionDto.setPatient(patientDto);
        return admissionDto;
    }
}
