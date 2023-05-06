package ru.iteco.fmh.converter.patient;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.converter.room.RoomEntityToRoomDtoRsConverter;
import ru.iteco.fmh.dto.patient.PatientCreateInfoDtoRs;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.user.Profile;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class PatientToPatientCreateInfoDtoRsConvertor implements Converter<Patient, PatientCreateInfoDtoRs> {
    private final RoomEntityToRoomDtoRsConverter roomEntityToRoomDtoRsConverter;

    @Override
    public PatientCreateInfoDtoRs convert(Patient patient) {
        Profile profile = patient.getProfile();

        LocalDate factDateIn = patient.getFactDateIn();
        LocalDate factDateOut = patient.getFactDateOut();
        LocalDate planDateIn = patient.getPlanDateIn();
        LocalDate planDateOut = patient.getPlanDateOut();

        PatientCreateInfoDtoRs patientCreateInfoDtoRs = PatientCreateInfoDtoRs.builder()
                .id(patient.getId())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .middleName(profile.getMiddleName())
                .birthDate(profile.getDateOfBirth())
                .status(patient.getStatus())
                .build();

        if (factDateIn != null) {
            patientCreateInfoDtoRs.setDateIn(factDateIn);
            patientCreateInfoDtoRs.setDateInBoolean(true);
        } else {
            patientCreateInfoDtoRs.setDateIn(planDateIn);
            patientCreateInfoDtoRs.setDateInBoolean(false);
        }

        if (factDateOut != null) {
            patientCreateInfoDtoRs.setDateOut(factDateOut);
            patientCreateInfoDtoRs.setDateOutBoolean(true);
        } else {
            patientCreateInfoDtoRs.setDateOut(planDateOut);
            patientCreateInfoDtoRs.setDateInBoolean(false);
        }

        if (patient.getRoom() != null) {
            patientCreateInfoDtoRs.setRoom(roomEntityToRoomDtoRsConverter.convert(patient.getRoom()));
        }
        return patientCreateInfoDtoRs;
    }
}

