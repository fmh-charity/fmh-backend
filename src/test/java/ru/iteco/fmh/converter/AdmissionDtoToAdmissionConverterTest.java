package ru.iteco.fmh.converter;

import org.junit.jupiter.api.Test;
import ru.iteco.fmh.converter.admission.AdmissionDtoToAdmissionConverter;
import ru.iteco.fmh.dao.repository.PatientRepository;
import ru.iteco.fmh.dao.repository.RoomRepository;
import ru.iteco.fmh.dto.admission.AdmissionDto;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.Room;
import ru.iteco.fmh.model.admission.Admission;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.iteco.fmh.TestUtils.getAdmissionDto;
import static ru.iteco.fmh.TestUtils.getPatient;
import static ru.iteco.fmh.TestUtils.getRoom;

public class AdmissionDtoToAdmissionConverterTest {
    PatientRepository patientRepository = mock(PatientRepository.class);
    RoomRepository roomRepository = mock(RoomRepository.class);

    AdmissionDtoToAdmissionConverter sut =
            new AdmissionDtoToAdmissionConverter(patientRepository, roomRepository);

    @Test
    void convertShouldPassSuccess() {
        // given
        AdmissionDto admissionDto = getAdmissionDto();
        admissionDto.setPatientId(1);
        admissionDto.setRoomId(1);
        Patient patient = getPatient();
        patient.setId(1);
        Room room = getRoom();
        room.setId(1);
        System.out.println(admissionDto);
        when(patientRepository.findPatientById(any())).thenReturn(patient);
        when(roomRepository.findRoomById(any())).thenReturn(room);

        Admission admission = sut.convert(admissionDto);

        assertAll(
                () -> assertEquals(admissionDto.getId(), admission.getId()),
                () -> assertEquals(admissionDto.getPatientId(), admission.getPatient().getId()),
                () -> assertEquals(admissionDto.getPlanDateIn(), admission.getPlanDateIn().toEpochMilli()),
                () -> assertNull(admission.getPlanDateOut()),
                () -> assertNull(admissionDto.getPlanDateOut()),
                () -> assertNull(admission.getFactDateIn()),
                () -> assertNull(admissionDto.getFactDateIn()),
                () -> assertNull(admission.getFactDateOut()),
                () -> assertNull(admissionDto.getFactDateOut()),
                () -> assertEquals(admissionDto.getStatus(), admission.getStatus()),
                () -> assertEquals(admissionDto.getRoomId(), admission.getRoom().getId()),
                () -> assertEquals(admissionDto.getComment(), admission.getComment())
        );
    }
}
