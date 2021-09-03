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

        Patient patient = dto.getPatientId() != 0 ? patientRepository.findPatientById(dto.getPatientId()) : null;
        Room room = dto.getRoomId() != 0 ? roomRepository.findRoomById(dto.getRoomId()) : null;

        admission.setPatient(patient);
        admission.setRoom(room);
        return admission;
    }
}
