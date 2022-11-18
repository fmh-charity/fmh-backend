package ru.iteco.fmh.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.controller.PatientsController;
import ru.iteco.fmh.dao.repository.PatientRepository;
import ru.iteco.fmh.dto.admission.AdmissionDto;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.dto.wish.WishDto;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.admission.AdmissionsStatus;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static ru.iteco.fmh.TestUtils.*;
import static ru.iteco.fmh.model.admission.AdmissionsStatus.*;


// ТЕСТЫ ЗАВЯЗАНЫ НА ТЕСТОВЫЕ ДАННЫЕ В БД!!
@RunWith(SpringRunner.class)
@SpringBootTest()
public class PatientsControllerTest {
    @Autowired
    PatientsController sut;

    @Autowired
    PatientRepository patientRepository;
    @Autowired
    ConversionService conversionService;

    @Test
    public void getAllPatientsByStatusTestShouldPassSuccess() {
        // given
        int countExpected = 3;
        int countActive = 2;
        int countDischarged = 1;
        int countAll = 6;

        int allPatients = (int) Stream.of(getAdmissionPatient(DISCHARGED), getAdmissionPatient(EXPECTED), getAdmissionPatient(ACTIVE)).count();
        int activePatients = (int) Stream.of(getAdmissionPatient(ACTIVE)).count();
        int dischargedPatients = (int) Stream.of(getAdmissionPatient(DISCHARGED)).count();
        int expectedPatients = (int) Stream.of(getAdmissionPatient(EXPECTED)).count();

        assertAll(
                () -> assertEquals(countExpected, expectedPatients),
                () -> assertEquals(countActive, activePatients),
                () -> assertEquals(countDischarged, dischargedPatients),
                () -> assertEquals(countAll, allPatients)
        );
    }

    @Test
    public void createPatientShouldPassSuccess() {
        //given
        PatientDto givenDto = getPatientDto();
        givenDto.setId(0);

        PatientDto resultDto = sut.createPatient(givenDto);

        Integer resultId = resultDto.getId();

        assertNotNull(resultId);

        givenDto.setId(resultId);
        assertEquals(givenDto, resultDto);

        // AFTER - deleting result entity
        patientRepository.deleteById(resultId);
    }

    @Test
    public void updatePatientShouldPassSuccess() {
        // given
        int patientId = 1;
        PatientDto given = conversionService.convert(patientRepository.findById(patientId).get(), PatientDto.class);
        assert given != null;
        String initialLastName = given.getLastName();
        given.setLastName("newLastName");

        PatientDto result = sut.updatePatient(given);
        result.setCurrentAdmission(null);
        result.setAdmissions(new HashSet<>());

        assertEquals(given, result);

        //after
        result.setLastName(initialLastName);
        patientRepository.save(Objects.requireNonNull(conversionService.convert(result, Patient.class)));
    }

    @Test
    public void getPatientShouldPassSuccess() {
        // given
        int patientId = 1;
        String patientFirstName = "Patient1-firstname";

        PatientDto result = sut.getPatient(patientId);

        assertEquals(patientFirstName, result.getFirstName());
    }

    @Test
    public void getAdmissionsShouldPassSuccess() {
        // given
        int patientId = 6;
        int admissionsCount = 2;
        String admissionComment0 = "admission6-comment";
        String admissionComment1 = "admission7-comment";

        List<AdmissionDto> result = sut.getAdmissions(patientId);

        assertAll(
                () -> assertEquals(admissionsCount, result.size()),
                () -> assertEquals(admissionComment0, result.get(0).getComment()),
                () -> assertEquals(admissionComment1, result.get(1).getComment())
        );
    }

    @Test
    public void getPatientAllWishesShouldPassSuccess() {
        //given
        int patientId = 1;
        List<String> expected = List.of("wish-title1", "wish-title7", "wish-title8", "wish-title6",
                "wish-title4");

        List<String> result = sut.getAllWishes(patientId).stream()
                .map(WishDto::getTitle).collect(Collectors.toList());

        assertEquals(expected, result);
    }

    @Test
    public void getPatientOpenInProgressWishes() {
        //given
        int patientId = 1;
        List<String> expected = List.of("wish-title1", "wish-title7", "wish-title8", "wish-title6");

        List<String> result = sut.getOpenInProgressWishes(patientId).stream()
                .map(WishDto::getTitle).collect(Collectors.toList());

        assertEquals(expected, result);
    }

    private Patient getAdmissionPatient(AdmissionsStatus admissionsStatus) {
        return Patient.builder()
                .id(Integer.valueOf(getNumeric(2)))
                .firstName(getAlphabeticString())
                .lastName(getAlphabeticString())
                .middleName(getAlphabeticString())
                .birthDate(Instant.now())
                .currentAdmission(getAdmission(admissionsStatus))
                .build();
    }
}
