package ru.iteco.fmh.converter.patient;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dto.patient.PatientCreateInfoDtoRq;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.Room;
import ru.iteco.fmh.service.room.RoomService;

@Component
@RequiredArgsConstructor
public class PatientCreateInfoDtoRqToPatientConvertor implements Converter<PatientCreateInfoDtoRq, Patient> {
    private final RoomService roomService;

    @Override
    public Patient convert(@NonNull PatientCreateInfoDtoRq dto) {
        Patient patient = new Patient();
        BeanUtils.copyProperties(dto, patient);

        if (dto.isDateInBoolean()) {
            patient.setFactDateIn(dto.getDateIn());
        } else {
            patient.setPlanDateIn(dto.getDateIn());
        }

        if (dto.isDateOutBoolean()) {
            patient.setFactDateOut(dto.getDateOut());
        } else {
            patient.setPlanDateOut(dto.getDateOut());
        }
        if (dto.getRoomId() != null) {
            Room room = roomService.findByIdAndDeletedIsFalse(dto.getRoomId());
            patient.setRoom(room);
        }

        return patient;
    }
}

