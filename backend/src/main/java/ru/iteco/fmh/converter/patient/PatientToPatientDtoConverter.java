package ru.iteco.fmh.converter.patient;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.converter.room.RoomEntityToRoomDtoRsConverter;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.user.Profile;

import java.time.LocalDate;

/**
 * конвертер из {@link Patient} в {@link PatientDto}//для «Пациенты» ( Создание пациента, изменение пациента)
 */
@Component
@RequiredArgsConstructor
public class PatientToPatientDtoConverter implements Converter<Patient, PatientDto> {
    private final RoomEntityToRoomDtoRsConverter roomEntityToRoomDtoRsConverter;

    @Override
    public PatientDto convert(Patient patient) {
        Profile profile = patient.getProfile();

        LocalDate factDateIn = patient.getFactDateIn();
        LocalDate factDateOut = patient.getFactDateOut();
        LocalDate planDateIn = patient.getPlanDateIn();
        LocalDate planDateOut = patient.getPlanDateOut();

        PatientDto patientDto = PatientDto.builder()
                .id(patient.getId())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .middleName(profile.getMiddleName())
                .birthDate(profile.getDateOfBirth())
                .status(patient.getStatus())
                .build();

        if (factDateIn != null) {
            patientDto.setDateIn(factDateIn);
            patientDto.setDateInBoolean(true);
        } else {
            patientDto.setDateIn(planDateIn);
            patientDto.setDateInBoolean(false);
        }

        if (factDateOut != null) {
            patientDto.setDateOut(factDateOut);
            patientDto.setDateOutBoolean(true);
        } else {
            patientDto.setDateOut(planDateOut);
            patientDto.setDateInBoolean(false);
        }

        if (patient.getRoom() != null) {
            patientDto.setRoom(roomEntityToRoomDtoRsConverter.convert(patient.getRoom()));
        }
        return patientDto;
    }
}
