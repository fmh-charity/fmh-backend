package ru.iteco.fmh.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.convert.ConversionService;
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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static ru.iteco.fmh.TestUtils.*;
import static ru.iteco.fmh.model.admission.AdmissionsStatus.ACTIVE;
import static ru.iteco.fmh.model.admission.AdmissionsStatus.DISCHARGED;
import static ru.iteco.fmh.model.admission.AdmissionsStatus.EXPECTED;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PatientServiceTest {
    @MockBean
    PatientRepository patientRepository;
    @MockBean
    AdmissionRepository admissionRepository;

    @Autowired
    PatientService sut;

    @Autowired
    ConversionService conversionService;

    @Test
    public void getAllPatientsByStatusShouldPassSuccess() {
        when(patientRepository.findAll()).thenReturn(getPatientList());

        // given
        List<String> statusListAll = List.of(EXPECTED.name(),
                ACTIVE.name(), DISCHARGED.name());
        List<String> statusListDISCHARGED = List.of(DISCHARGED.name());
        List<String> statusListACTIVE = List.of(ACTIVE.name());
        List<String> statusListEXPECTED = List.of(EXPECTED.name());
        List<String> statusListMIXED = List.of(EXPECTED.name(), DISCHARGED.name());
        List<String> statusListEmpty = List.of();

        // result
        List<PatientAdmissionDto> resultAll = sut.getAllPatientsByStatus(statusListAll);
        List<PatientAdmissionDto> resultDISCHARGED = sut.getAllPatientsByStatus(statusListDISCHARGED);
        List<PatientAdmissionDto> resultACTIVE = sut.getAllPatientsByStatus(statusListACTIVE);
        List<PatientAdmissionDto> resultEXPECTED = sut.getAllPatientsByStatus(statusListEXPECTED);
        List<PatientAdmissionDto> resultMIXED = sut.getAllPatientsByStatus(statusListMIXED);
        List<PatientAdmissionDto> resultEmpty = sut.getAllPatientsByStatus(statusListEmpty);

        assertAll(
                () -> assertEquals(4, resultAll.size()),
                () -> assertEquals(1, resultDISCHARGED.size()),
                () -> assertEquals(1, resultACTIVE.size()),
                () -> assertEquals(2, resultEXPECTED.size()),
                () -> assertEquals(3, resultMIXED.size()),
                () -> assertEquals(0, resultEmpty.size())
        );
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

    private List<Patient> getPatientList() {
        List<Patient> patientList = new ArrayList<>();
        patientList.add(getAdmissionPatient(DISCHARGED));
        patientList.add(getAdmissionPatient(ACTIVE));
        patientList.add(getAdmissionPatient(EXPECTED));
        patientList.add(getEmptyAdmissionPatient());
        return patientList;
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
