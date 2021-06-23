package ru.iteco.fmh.converter.admission;

import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import ru.iteco.fmh.converter.patient.fromPatientDto.IPatientDtoToPatientConverter;
import ru.iteco.fmh.dto.admission.AdmissionDto;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.admission.Admission;

/**
 * конвертер из {@link AdmissionDto} в {@link Admission}
 */
public class AdmissionDtoToAdmissionConverter implements Converter<AdmissionDto, Admission> {

    private final IPatientDtoToPatientConverter patientConverter;

    public AdmissionDtoToAdmissionConverter(IPatientDtoToPatientConverter patientConverter) {
        this.patientConverter = patientConverter;
    }

    @Override
    public Admission convert(AdmissionDto admissionDto) {
        Admission admission = new Admission();
        BeanUtils.copyProperties(admissionDto, admission);
        Patient patient = patientConverter.convert(admissionDto.getPatient());
        admission.setPatient(patient);
        return admission;
    }

}
