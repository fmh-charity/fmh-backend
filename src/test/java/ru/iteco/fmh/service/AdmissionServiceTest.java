package ru.iteco.fmh.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.dao.repository.AdmissionRepository;
import ru.iteco.fmh.dto.admission.AdmissionDto;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.admission.Admission;
import ru.iteco.fmh.model.admission.AdmissionsStatus;
import ru.iteco.fmh.service.admission.AdmissionService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ru.iteco.fmh.TestUtils.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdmissionServiceTest {
    @Autowired
    AdmissionService sut;

    @Autowired
    ConversionServiceFactoryBean factoryBean;

    @MockBean
    AdmissionRepository admissionRepository;

    @Test
    public void getPatientAdmissionsShouldPassSuccess() {
        // given
        Admission admission1 = getAdmission();
        Admission admission2 = getAdmission();
        AdmissionDto admissionDto1 = admissionToDto(admission1);
        AdmissionDto admissionDto2 = admissionToDto(admission2);
        int patientId = 1;

        when(admissionRepository.findAllByPatient_IdAndDeletedIsFalse(any()))
                .thenReturn(List.of(admission1,admission2));

        List<AdmissionDto> result = sut.getPatientAdmissions(patientId);

        assertEquals(List.of(admissionDto1,admissionDto2), result);
    }

    @Test
    public void getAdmissionShouldPassSuccess() {
        // given
        Admission admission = getAdmission();
        AdmissionDto admissionDto = admissionToDto(admission);
        int admissionId = 1;

        when(admissionRepository.findById(any())).thenReturn(Optional.of(admission));

        AdmissionDto result = sut.getAdmission(admissionId);

        assertEquals(admissionDto, result);
    }

    @Test
    public void createAdmissionShouldPassSuccess() {
        // given
        Admission admission = getAdmission();
        AdmissionDto admissionDto = admissionToDto(admission);

        when(admissionRepository.save(any())).thenReturn(admission);

        int result = sut.createAdmission(admissionDto);

        assertEquals(admission.getId(), result);
    }

    @Test
    public void updateAdmissionShouldPassSuccess() {
        // given
        Admission expected = getAdmission();
        AdmissionDto admissionDto = admissionToDto(expected);

        when(admissionRepository.save(any())).thenReturn(expected);

        AdmissionDto result = sut.updateAdmission(admissionDto);

        assertAll(
                ()-> assertEquals(expected.getId(), result.getId()),
                ()-> assertEquals(patientToDto(expected.getPatient()), result.getPatient()),
                ()-> assertEquals(expected.getComment(), result.getComment()),
                ()-> assertEquals(expected.getStatus(), result.getStatus()),
                ()-> assertEquals(expected.getRoom(), result.getRoom()),
                ()-> assertEquals(expected.getFactDateIn(), result.getFactDateIn()),
                ()-> assertEquals(expected.getFactDateOut(), result.getFactDateOut()),
                ()-> assertEquals(expected.getPlanDateIn(), result.getPlanDateIn()),
                ()-> assertEquals(expected.getPlanDateOut(), result.getPlanDateOut())
        );
    }

    private Admission getAdmission() {
        return Admission.builder()
                .patient(Patient.builder().build())
                .comment(getAlphabeticStringR())
                .id(Integer.valueOf(getNumeric(2)))
                .factDateIn(LocalDateTime.now().withNano(0))
                .factDateOut(LocalDateTime.now().withNano(0))
                .planDateIn(LocalDateTime.now().withNano(0))
                .planDateOut(LocalDateTime.now().withNano(0))
                .status(AdmissionsStatus.ACTIVE)
                .build();
    }

    private AdmissionDto admissionToDto(Admission admission) {
        ConversionService conversionService = factoryBean.getObject();
        return conversionService.convert(admission, AdmissionDto.class);
    }

    private PatientDto patientToDto(Patient patient) {
        ConversionService conversionService = factoryBean.getObject();
        return conversionService.convert(patient, PatientDto.class);
    }
}