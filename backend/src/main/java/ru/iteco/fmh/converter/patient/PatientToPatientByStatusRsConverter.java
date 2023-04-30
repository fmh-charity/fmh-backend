package ru.iteco.fmh.converter.patient;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.converter.room.RoomEntityToRoomDtoRsConverter;
import ru.iteco.fmh.dto.patient.PatientByStatusRs;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.user.Profile;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class PatientToPatientByStatusRsConverter implements Converter<Patient, PatientByStatusRs> {
    private final RoomEntityToRoomDtoRsConverter roomEntityToRoomDtoRsConverter;

    @Override
    public PatientByStatusRs convert(Patient patient) {
        Profile profile = patient.getProfile();

        LocalDate factDateIn = patient.getFactDateIn();
        LocalDate factDateOut = patient.getFactDateOut();
        LocalDate planDateIn = patient.getPlanDateIn();
        LocalDate planDateOut = patient.getPlanDateOut();

        PatientByStatusRs patientByStatusRs = PatientByStatusRs.builder()
                .id(patient.getId())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .middleName(profile.getMiddleName())
                .birthday(profile.getDateOfBirth())
                .status(patient.getStatus())
                .build();

        if (factDateIn != null) {
            patientByStatusRs.setDateIn(factDateIn);
            patientByStatusRs.setDateInBoolean(true);
        } else {
            patientByStatusRs.setDateIn(planDateIn);
            patientByStatusRs.setDateInBoolean(false);
        }

        if (factDateOut != null) {
            patientByStatusRs.setDateOut(factDateOut);
            patientByStatusRs.setDateOutBoolean(true);
        } else {
            patientByStatusRs.setDateOut(planDateOut);
            patientByStatusRs.setDateInBoolean(false);
        }
        if (patient.getRoom() != null) {
            patientByStatusRs.setRoom(roomEntityToRoomDtoRsConverter.convert(patient.getRoom()));
        }
        return patientByStatusRs;
    }
}
