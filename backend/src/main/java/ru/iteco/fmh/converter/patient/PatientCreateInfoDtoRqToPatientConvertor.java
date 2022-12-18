package ru.iteco.fmh.converter.patient;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dao.repository.RoomRepository;
import ru.iteco.fmh.dto.patient.PatientCreateInfoDtoRq;
import ru.iteco.fmh.exceptions.NotFoundException;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.Room;

@Component
@RequiredArgsConstructor
public class PatientCreateInfoDtoRqToPatientConvertor implements Converter<PatientCreateInfoDtoRq, Patient> {
    private final RoomRepository roomRepository;

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
            Room room = roomRepository.findByIdAndDeletedIsFalse(dto.getRoomId())
                    .orElseThrow(() -> new NotFoundException("Доступной палаты с данным ID не найдено"));
            patient.setRoom(room);
        }

        return patient;
    }
}

