package ru.iteco.fmh.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.dao.repository.AdmissionRepository;
import ru.iteco.fmh.dao.repository.PatientRepository;
import ru.iteco.fmh.dto.patient.PatientAdmissionDto;
import ru.iteco.fmh.dto.patient.PatientCreateInfoDtoRq;
import ru.iteco.fmh.dto.patient.PatientCreateInfoDtoRs;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.admission.Admission;
import ru.iteco.fmh.model.admission.AdmissionsStatus;
import ru.iteco.fmh.service.patient.PatientService;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static ru.iteco.fmh.TestUtils.*;
import static ru.iteco.fmh.model.admission.AdmissionsStatus.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PatientServiceTest {
    static final int pages = 8;
    static final int elements = 8;
    @MockBean
    PatientRepository patientRepository;
    @MockBean
    AdmissionRepository admissionRepository;
    @Autowired
    PatientService sut;
    @Autowired
    ConversionService conversionService;

    @Test
    public void getAllPatientsByActiveStatusShouldPassSuccess() {
        List<Patient> patientList = List.of(getAdmissionPatient(AdmissionsStatus.ACTIVE));
        Pageable pageableList = PageRequest.of(pages, elements, Sort.by("inDate"));
        Page<Patient> pageableResult = new PageImpl<>(patientList, pageableList, 8);
        when(patientRepository.findAllByStatusIn(List.of(ACTIVE), pageableList))
                .thenReturn(pageableResult);

        List<PatientDto> expected = patientList.stream()
                .map(patient -> conversionService.convert(patient, PatientDto.class)).collect(Collectors.toList());
        when(patientRepository.findAllByStatusIn(List.of(ACTIVE), any())).thenReturn(pageableResult);

        List<PatientDto> result = sut.getAllPatientsByStatus(null, pages, elements, true)
                .getElements().stream().toList();

        assertEquals(expected, result);
    }

    @Test
    public void getAllPatientsWithInactiveStatusShouldPassSuccess() {
        List<Patient> patientList = List.of(getAdmissionPatient(DISCHARGED), getAdmissionPatient(EXPECTED));
        Pageable pageableList = PageRequest.of(pages, elements, Sort.by("inDate"));
        Page<Patient> pageableResult = new PageImpl<>(patientList, pageableList, 8);
        when(patientRepository.findAllByStatusIn(List.of(DISCHARGED, EXPECTED), pageableList))
                .thenReturn(pageableResult);

        List<PatientDto> expected = patientList.stream()
                .map(patient -> conversionService.convert(patient, PatientDto.class)).collect(Collectors.toList());
        when(patientRepository.findAllByStatusIn(List.of(DISCHARGED, EXPECTED), any())).thenReturn(pageableResult);

        List<PatientDto> result = sut.getAllPatientsByStatus(null, pages, elements, true)
                .getElements().stream().toList();

        assertEquals(expected, result);
    }


    @Test
    public void getAllPatientsByStatusShouldPassSuccess() {
        List<Patient> patientList = List.of(getAdmissionPatient(DISCHARGED), getAdmissionPatient(EXPECTED), getAdmissionPatient(ACTIVE));
        Pageable pageableList = PageRequest.of(pages, elements, Sort.by("inDate"));
        Page<Patient> pageableResult = new PageImpl<>(patientList, pageableList, 8);
        when(patientRepository.findAllByStatusIn(List.of(DISCHARGED, EXPECTED, ACTIVE), pageableList))
                .thenReturn(pageableResult);

        List<PatientDto> expected = patientList.stream()
                .map(patient -> conversionService.convert(patient, PatientDto.class)).collect(Collectors.toList());
        when(patientRepository.findAllByStatusIn(List.of(DISCHARGED, EXPECTED, ACTIVE), any())).thenReturn(pageableResult);

        List<PatientDto> result = sut.getAllPatientsByStatus(null, pages, elements, true)
                .getElements().stream().toList();

        assertEquals(expected, result);


    }

    @Test
    public void createPatientShouldPassSuccess() {
        // given
        PatientCreateInfoDtoRq patientRq = getPatientCreateInfoDtoRq();
        Patient patient = conversionService.convert(patientRq, Patient.class);
        patient.setId(12);
        PatientCreateInfoDtoRs patientRs = conversionService.convert(patient, PatientCreateInfoDtoRs.class  );

        when(patientRepository.save(any())).thenReturn(patient);
        PatientCreateInfoDtoRs result = sut.createPatient(patientRq);

        assertEquals(patientRs, result);
    }

    @Test
    public void updatePatientShouldPassSuccess() {
        // given
        Patient patient = getPatient();
        patient.setCurrentAdmission(null);
        PatientDto given = conversionService.convert(patient, PatientDto.class);

        assert given != null;
        given.setFirstName("Test");
        given.setDeleted(true);

        patient.setFirstName("Test");
        patient.setDeleted(true);

        when(patientRepository.findPatientById(any())).thenReturn(patient);
        when(patientRepository.save(any())).thenReturn(patient);
        PatientDto result = sut.updatePatient(given);

        assertEquals(given, result);
    }

    @Test
    public void getPatientShouldPassSuccess() {
        // given
        Patient patient = getPatient();
        patient.setCurrentAdmission(null);

        when(patientRepository.findById(any())).thenReturn(Optional.of(patient));

        PatientDto expected = conversionService.convert(patient, PatientDto.class);
        PatientDto result = sut.getPatient(0);

        assertEquals(expected, result);
    }

    private Patient getAdmissionPatient(AdmissionsStatus admissionsStatus) {
        return Patient.builder()
                .id(Integer.valueOf(getNumeric(2)))
                .firstName(getAlphabeticString())
                .lastName(getAlphabeticString())
                .middleName(getAlphabeticString())
                .birthDate(LocalDate.now())
                .currentAdmission(getAdmission(admissionsStatus))
                .build();
    }

    private Patient getEmptyAdmissionPatient() {
        return Patient.builder()
                .firstName(getAlphabeticString())
                .lastName(getAlphabeticString())
                .middleName(getAlphabeticString())
                .birthDate(LocalDate.now())
                .currentAdmission(null)
                .build();
    }

    private Admission getAdmission(AdmissionsStatus admissionsStatus) {
        return Admission.builder()
                .status(admissionsStatus)
                .build();
    }
}
