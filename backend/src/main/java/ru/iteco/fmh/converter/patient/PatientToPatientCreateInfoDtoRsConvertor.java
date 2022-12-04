package ru.iteco.fmh.converter.patient;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dto.patient.PatientCreateInfoDtoRs;
import ru.iteco.fmh.model.Patient;

@Component
@RequiredArgsConstructor
public class PatientToPatientCreateInfoDtoRsConvertor implements Converter<Patient, PatientCreateInfoDtoRs> {
    @Override
    public PatientCreateInfoDtoRs convert(Patient patient) {
        PatientCreateInfoDtoRs patientCreateInfoDtoRs = new PatientCreateInfoDtoRs();
        BeanUtils.copyProperties(patient, patientCreateInfoDtoRs);
        return patientCreateInfoDtoRs;
    }
}

