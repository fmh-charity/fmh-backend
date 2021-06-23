package ru.iteco.fmh.converter.admission;

import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import ru.iteco.fmh.converter.patient.fromPatient.IPatientToPatientDtoConverter;
import ru.iteco.fmh.dto.admission.AdmissionDto;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.model.admission.Admission;

/**
 * конвертер из {@link Admission} в {@link AdmissionDto}
 */
public class AdmissionToAdmissionDtoConverter implements Converter<Admission, AdmissionDto> {

    private final IPatientToPatientDtoConverter patientConverter;

    public AdmissionToAdmissionDtoConverter(IPatientToPatientDtoConverter patientConverter) {
        this.patientConverter = patientConverter;
    }

    @Override
    public AdmissionDto convert(Admission admission) {
        AdmissionDto admissionDto = new AdmissionDto();
        BeanUtils.copyProperties(admission, admissionDto);
        PatientDto patientDto = patientConverter.convert(admission.getPatient());
        admissionDto.setPatient(patientDto);
        return admissionDto;
    }

}


