package ru.iteco.fmh.converter.patient;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dao.repository.AdmissionRepository;
import ru.iteco.fmh.dto.patient.PatientInfoDto;
import ru.iteco.fmh.model.Patient;

import java.time.Instant;
import java.time.LocalDate;

/**
 * конвертер из {@link PatientInfoDto} в {@link Patient}
 */
@Component
@RequiredArgsConstructor
public class PatientDtoToPatientConverter implements Converter<PatientInfoDto, Patient> {

    @Override
    public Patient convert(@NonNull PatientInfoDto dto) {
        Patient entity = new Patient();
        BeanUtils.copyProperties(dto, entity);

        return entity;
    }
}
