package ru.iteco.fmh.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.dao.repository.PatientRepository;
import ru.iteco.fmh.dto.patient.*;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.PatientStatus;
import ru.iteco.fmh.service.patient.PatientService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.*;
import static ru.iteco.fmh.TestUtils.*;
import static ru.iteco.fmh.model.PatientStatus.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PatientServiceTest {
    @Autowired
    PatientRepository patientRepository;

    @Autowired
    PatientService sut;

    @Autowired
    ConversionService conversionService;

    @Test
    public void getAllPatientsByStatusShouldPassSuccess() {
        // given
        List<String> statusListAll = List.of(EXPECTED.name(),
                ACTIVE.name(), DISCHARGED.name());
        List<String> statusListDISCHARGED = List.of(DISCHARGED.name());
        List<String> statusListACTIVE = List.of(ACTIVE.name());
        List<String> statusListEXPECTED = List.of(EXPECTED.name());
        List<String> statusListMIXED = List.of(EXPECTED.name(), DISCHARGED.name());
        List<String> statusListEmpty = List.of();

        // result
        List<PatientByStatusRs> resultAll = sut.getAllPatientsByStatus(statusListAll);
        List<PatientByStatusRs> resultDISCHARGED = sut.getAllPatientsByStatus(statusListDISCHARGED);
        List<PatientByStatusRs> resultACTIVE = sut.getAllPatientsByStatus(statusListACTIVE);
        List<PatientByStatusRs> resultEXPECTED = sut.getAllPatientsByStatus(statusListEXPECTED);
        List<PatientByStatusRs> resultMIXED = sut.getAllPatientsByStatus(statusListMIXED);
        List<PatientByStatusRs> resultEmpty = sut.getAllPatientsByStatus(statusListEmpty);

        assertAll(
                () -> assertEquals(6, resultAll.size()),
                () -> assertEquals(1, resultDISCHARGED.size()),
                () -> assertEquals(2, resultACTIVE.size()),
                () -> assertEquals(3, resultEXPECTED.size()),
                () -> assertEquals(4, resultMIXED.size()),
                () -> assertEquals(0, resultEmpty.size())
        );
    }

    @Test
    public void createPatientShouldPassSuccess() {
        PatientCreateInfoDtoRq patientRq = getPatientCreateInfoDtoRq();

        PatientCreateInfoDtoRs result = sut.createPatient(patientRq);

        assertNotNull(result.getId());

        patientRepository.deleteById(result.getId());
    }

    @Test
    public void updatePatientShouldPassSuccess() {
        // given
        Patient patient = patientRepository.findPatientById(4);

        PatientUpdateInfoDtoRq given = PatientUpdateInfoDtoRq.builder()
                .firstName("PatientSixFirstname")
                .lastName(patient.getProfile().getLastName())
                .middleName(patient.getProfile().getMiddleName())
                .birthDate(patient.getProfile().getDateOfBirth())
                .status(patient.getStatus())
                .build();
        PatientUpdateInfoDtoRs result = sut.updatePatient(patient.getId(), given);

        assertEquals(given.getFirstName(), result.getFirstName());
        assertNotEquals(patient.getProfile().getFirstName(), result.getFirstName());
    }

    @Test
    public void getPatientShouldPassSuccess() {
        // given
        Patient patient = getPatient();
        patient = patientRepository.save(patient);
        PatientDto expected = conversionService.convert(patient, PatientDto.class);

        PatientDto result = sut.getPatient(patient.getId());

        assertEquals(expected, result);

        patientRepository.deleteById(patient.getId());
    }

    private List<Patient> getPatientList() {
        List<Patient> patientList = new ArrayList<>();
        patientList.add(getAdmissionPatient(DISCHARGED));
        patientList.add(getAdmissionPatient(ACTIVE));
        patientList.add(getAdmissionPatient(EXPECTED));
        patientList.add(getEmptyAdmissionPatient());
        return patientList;
    }

    private Patient getAdmissionPatient(PatientStatus patientStatus) {
        return Patient.builder()
                .id(Integer.valueOf(getNumeric(2)))
                .profile(getProfile())
                .status(patientStatus)
                .build();
    }

    private Patient getEmptyAdmissionPatient() {
        return Patient.builder()
                .id(Integer.valueOf(getNumeric(2)))
                .deleted(false)
                .profile(getProfile())
                .build();
    }
}
