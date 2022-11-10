package ru.iteco.fmh.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.iteco.fmh.dao.repository.AdmissionRepository;
import ru.iteco.fmh.dao.repository.PatientRepository;
import ru.iteco.fmh.dao.repository.RoomRepository;
import ru.iteco.fmh.dto.admission.AdmissionDto;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.Room;
import ru.iteco.fmh.model.admission.Admission;
import ru.iteco.fmh.model.admission.AdmissionsStatus;
import ru.iteco.fmh.service.admission.AdmissionService;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ru.iteco.fmh.TestUtils.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdmissionServiceTest {
    @Autowired
    AdmissionService sut;

    @Autowired
    ConversionService conversionService;

    @MockBean
    AdmissionRepository admissionRepository;

    @MockBean
    PatientRepository patientRepository;

    @MockBean
    RoomRepository roomRepository;

    @Test
    public void getPatientAdmissionsShouldPassSuccess() {
        // given
        Admission admission1 = getAdmission();
        Admission admission2 = getAdmission();
        AdmissionDto admissionDto1 = conversionService.convert(admission1, AdmissionDto.class);
        AdmissionDto admissionDto2 = conversionService.convert(admission2, AdmissionDto.class);
        int patientId = 1;

        when(admissionRepository.findAllByPatient_IdAndDeletedIsFalse(any()))
                .thenReturn(List.of(admission1,admission2));

        List<AdmissionDto> result = sut.getPatientAdmissions(patientId);

        assertEquals(List.of(admissionDto1,admissionDto2), result);
    }

    @Transactional
    @Test
    public void getAdmissionShouldPassSuccess() {
        // given
        Admission admission = getAdmission();
        AdmissionDto admissionDto = conversionService.convert(admission, AdmissionDto.class);
        int admissionId = 1;

        when(admissionRepository.findById(any())).thenReturn(Optional.of(admission));

        AdmissionDto result = sut.getAdmission(admissionId);

        assertEquals(admissionDto, result);
    }

    @Transactional
    @Test
    public void createOrUpdateAdmissionShouldPassSuccess() {
        // given
        Admission admission = getAdmission();
        AdmissionDto givenDto = conversionService.convert(admission, AdmissionDto.class);

        when(admissionRepository.save(any())).thenReturn(admission);
        when(roomRepository.findRoomById(any())).thenReturn(admission.getRoom());
        when(admissionRepository.findAdmissionsByPatientId(any())).thenReturn(Set.of(admission));
        when(patientRepository.findPatientById(any())).thenReturn(admission.getPatient());
        when(patientRepository.save(any())).thenReturn(admission.getPatient());

        AdmissionDto result = sut.createOrUpdateAdmission(givenDto);

        assertEquals(givenDto, result);
    }

    @Test
    public void deleteCurrentAdmissionTest() {
        Admission admissionActive = getAdmission();
        Admission admissionDeleted = admissionActive;
        Patient patient = admissionActive.getPatient();

        admissionDeleted.setDeleted(true);
        patient.setCurrentAdmission(admissionDeleted);

        when(admissionRepository.findById(any())).thenReturn(Optional.of(admissionActive));
        when(admissionRepository.save(any())).thenReturn(admissionDeleted);
        when(patientRepository.save(any())).thenReturn(patient);
        sut.deleteAdmissionById(admissionActive.getId());

        assertEquals(admissionDeleted, admissionActive);
        assertEquals(null, patient.getCurrentAdmission());
    }
}