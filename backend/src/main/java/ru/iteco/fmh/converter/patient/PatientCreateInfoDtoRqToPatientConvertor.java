package ru.iteco.fmh.converter.patient;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dto.patient.PatientCreateInfoDtoRq;
import ru.iteco.fmh.model.Patient;

@Component
@RequiredArgsConstructor
public class PatientCreateInfoDtoRqToPatientConvertor implements Converter<PatientCreateInfoDtoRq, Patient> {

    @Override
    public Patient convert(@NonNull PatientCreateInfoDtoRq dto) {
        Patient patient = new Patient();
        BeanUtils.copyProperties(dto, patient);

        return patient;
    }
}
