package ru.iteco.fmh.converter.patient;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.user.Profile;

/**
 * конвертер из {@link PatientDto} в {@link Patient}
 */
@Component
@RequiredArgsConstructor
public class PatientDtoToPatientConverter implements Converter<PatientDto, Patient> {

    @Override
    public Patient convert(@NonNull PatientDto dto) {
        Patient entity = new Patient();
        BeanUtils.copyProperties(dto, entity);

        Profile profile = Profile.builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .middleName(dto.getMiddleName())
                .lastName(dto.getLastName())
                .deleted(false)
                .dateOfBirth(dto.getBirthDate())
                .build();
        entity.setProfile(profile);

        return entity;
    }
}
