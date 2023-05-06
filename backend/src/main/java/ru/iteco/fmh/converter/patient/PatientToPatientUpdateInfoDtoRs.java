package ru.iteco.fmh.converter.patient;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.converter.room.RoomEntityToRoomDtoRsConverter;
import ru.iteco.fmh.dto.patient.PatientUpdateInfoDtoRs;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.user.Profile;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class PatientToPatientUpdateInfoDtoRs implements Converter<Patient, PatientUpdateInfoDtoRs> {
    private final RoomEntityToRoomDtoRsConverter roomEntityToRoomDtoRsConverter;

    @Override
    public PatientUpdateInfoDtoRs convert(Patient patient) {
        Profile profile = patient.getProfile();

        LocalDate factDateIn = patient.getFactDateIn();
        LocalDate factDateOut = patient.getFactDateOut();
        LocalDate planDateIn = patient.getPlanDateIn();
        LocalDate planDateOut = patient.getPlanDateOut();

        PatientUpdateInfoDtoRs patientUpdateInfoDtoRs = PatientUpdateInfoDtoRs.builder()
                .id(patient.getId())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .middleName(profile.getMiddleName())
                .birthDate(profile.getDateOfBirth())
                .status(patient.getStatus())
                .build();

        if (factDateIn != null) {
            patientUpdateInfoDtoRs.setDateIn(factDateIn);
            patientUpdateInfoDtoRs.setDateInBoolean(true);
        } else {
            patientUpdateInfoDtoRs.setDateIn(planDateIn);
            patientUpdateInfoDtoRs.setDateInBoolean(false);
        }

        if (factDateOut != null) {
            patientUpdateInfoDtoRs.setDateOut(factDateOut);
            patientUpdateInfoDtoRs.setDateOutBoolean(true);
        } else {
            patientUpdateInfoDtoRs.setDateOut(planDateOut);
            patientUpdateInfoDtoRs.setDateInBoolean(false);
        }
        if (patient.getRoom() != null) {
            patientUpdateInfoDtoRs.setRoom(roomEntityToRoomDtoRsConverter.convert(patient.getRoom()));
        }
        return patientUpdateInfoDtoRs;
    }
}
