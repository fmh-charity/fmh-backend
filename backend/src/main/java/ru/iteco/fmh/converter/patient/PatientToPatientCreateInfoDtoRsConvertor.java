package ru.iteco.fmh.converter.patient;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.converter.room.RoomEntityToRoomDtoRsConverter;
import ru.iteco.fmh.dto.patient.PatientCreateInfoDtoRs;
import ru.iteco.fmh.model.Patient;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class PatientToPatientCreateInfoDtoRsConvertor implements Converter<Patient, PatientCreateInfoDtoRs> {
    private final RoomEntityToRoomDtoRsConverter roomEntityToRoomDtoRsConverter;

    @Override
    public PatientCreateInfoDtoRs convert(Patient patient) {
        LocalDate factDateIn = patient.getFactDateIn();
        LocalDate factDateOut = patient.getFactDateOut();
        LocalDate planDateIn = patient.getPlanDateIn();
        LocalDate planDateOut = patient.getPlanDateOut();

        PatientCreateInfoDtoRs patientCreateInfoDtoRs = PatientCreateInfoDtoRs.builder()
                .id(patient.getId())
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .middleName(patient.getMiddleName())
                .birthDate(patient.getBirthDate())
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

