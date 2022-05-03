package ru.iteco.fmh.converter.admission;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.iteco.fmh.dao.repository.PatientRepository;
import ru.iteco.fmh.dao.repository.RoomRepository;
import ru.iteco.fmh.dto.admission.AdmissionDto;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.Room;
import ru.iteco.fmh.model.admission.Admission;

import java.time.Instant;

/**
 * конвертер из {@link AdmissionDto} в {@link Admission}
 */
@Component
@RequiredArgsConstructor
public class AdmissionDtoToAdmissionConverter implements Converter<AdmissionDto, Admission> {
    private final PatientRepository patientRepository;
    private final RoomRepository roomRepository;

    @Override
    public Admission convert(@NonNull AdmissionDto dto) {
        Admission admission = new Admission();
        BeanUtils.copyProperties(dto, admission);

        Patient patient = dto.getPatientId() != null ? patientRepository.findPatientById(dto.getPatientId()) : null;
        Room room = dto.getRoomId() != null ? roomRepository.findRoomById(dto.getRoomId()) : null;

        admission.setPlanDateIn(dto.getPlanDateIn() != null ? Instant.ofEpochMilli(dto.getPlanDateIn()) : null);
        admission.setPlanDateOut(dto.getPlanDateOut() != null ? Instant.ofEpochMilli(dto.getPlanDateOut()) : null);
        admission.setFactDateIn(dto.getFactDateIn() != null ? Instant.ofEpochMilli(dto.getFactDateIn()) : null);
        admission.setFactDateOut(dto.getFactDateOut() != null ? Instant.ofEpochMilli(dto.getFactDateOut()) : null);

        admission.setPatient(patient);
        admission.setRoom(room);
        return admission;
    }
}
