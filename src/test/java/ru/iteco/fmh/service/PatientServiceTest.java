package ru.iteco.fmh.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.dao.repository.PatientRepository;
import ru.iteco.fmh.dto.patient.PatientAdmissionDto;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.admission.Admission;
import ru.iteco.fmh.model.admission.AdmissionsStatus;
import ru.iteco.fmh.service.patient.PatientService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ru.iteco.fmh.TestUtils.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PatientServiceTest {
    @MockBean
    PatientRepository patientRepository;

    @Autowired
    PatientService sut;

    @Autowired
    ConversionServiceFactoryBean factoryBean;

    @Test
    public void getAllPatientsByStatusShouldPassSuccess() {
        when(patientRepository.findAll()).thenReturn(getPatientList());

        // given
        List<String> statusListAll = List.of(AdmissionsStatus.EXPECTED.name(),
                AdmissionsStatus.ACTIVE.name(), AdmissionsStatus.DISCHARGED.name());
        List<String> statusListDISCHARGED = List.of(AdmissionsStatus.DISCHARGED.name());
        List<String> statusListACTIVE = List.of(AdmissionsStatus.ACTIVE.name());
        List<String> statusListEXPECTED = List.of(AdmissionsStatus.EXPECTED.name());
        List<String> statusListMIXED = List.of(AdmissionsStatus.EXPECTED.name(), AdmissionsStatus.DISCHARGED.name());
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
    public void createOrUpdatePatientShouldPassSuccess() {
        ConversionService conversionService = factoryBean.getObject();

        // given
        Patient patient = getPatient();

        when(patientRepository.save(any())).thenReturn(patient);

        // result
        PatientDto expected = conversionService.convert(patient, PatientDto.class);
        PatientDto result = sut.createOrUpdatePatient(expected);

        assertEquals(expected, result);
    }

    @Test
    public void getPatientShouldPassSuccess() {
        ConversionService conversionService = factoryBean.getObject();

        // given
        Patient patient = getPatient();

        when(patientRepository.getOne(any())).thenReturn(patient);

        PatientDto expected = conversionService.convert(patient, PatientDto.class);
        PatientDto result = sut.getPatient(0);

        assertEquals(expected, result);
    }

    private List<Patient> getPatientList() {
        List<Patient> patientList = new ArrayList<>();
        patientList.add(getAdmissionPatient(AdmissionsStatus.DISCHARGED));
        patientList.add(getAdmissionPatient(AdmissionsStatus.ACTIVE));
        patientList.add(getAdmissionPatient(AdmissionsStatus.EXPECTED));
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
