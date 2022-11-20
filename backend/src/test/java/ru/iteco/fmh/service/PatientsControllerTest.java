package ru.iteco.fmh.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.iteco.fmh.controller.PatientsController;
import ru.iteco.fmh.dao.repository.PatientRepository;
import ru.iteco.fmh.dto.admission.AdmissionDto;
import ru.iteco.fmh.dto.patient.PatientCreateInfoDtoRq;
import ru.iteco.fmh.dto.patient.PatientCreateInfoDtoRs;
import ru.iteco.fmh.dto.wish.WishDto;
import ru.iteco.fmh.dto.patient.PatientAdmissionDto;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.model.Patient;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

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

        List<PatientAdmissionDto> resultExpected = sut.getAllPatientsByStatus(List.of(EXPECTED.name()));
        List<PatientAdmissionDto> resultActive = sut.getAllPatientsByStatus(List.of(ACTIVE.name()));
        List<PatientAdmissionDto> resultDischarged = sut.getAllPatientsByStatus(List.of(DISCHARGED.name()));
        List<PatientAdmissionDto> resultAll = sut.getAllPatientsByStatus(List.of(EXPECTED.name(),
                ACTIVE.name(), DISCHARGED.name()));

        assertAll(
                () -> assertEquals(countExpected, resultExpected.size()),
                () -> assertEquals(countActive, resultActive.size()),
                () -> assertEquals(countDischarged, resultDischarged.size()),
                () -> assertEquals(countAll, resultAll.size())
        );
    }

    @Test
    public void createPatientShouldPassSuccess() {
        //given

        PatientCreateInfoDtoRq givenDto = getPatientCreateInfoDtoRq();

        PatientCreateInfoDtoRs resultDto = sut.createPatient(givenDto);

        assertAll(
                () -> assertEquals(givenDto.getFirstName(), resultDto.getFirstName()),
                () -> assertEquals(givenDto.getLastName(), resultDto.getLastName()),
                () -> assertEquals(givenDto.getMiddleName(), resultDto.getMiddleName()),
                () -> assertEquals(givenDto.getBirthDate(), resultDto.getBirthDate()),
                () -> assertNotNull(resultDto.getId())
        );

        // AFTER - deleting result entity
        patientRepository.deleteById(resultDto.getId());
    }

   /* @Test
    public void updatePatientShouldPassSuccess() {
        // given
        int patientId = 1;
        PatientDto given = conversionService.convert(patientRepository.findById(patientId).get(), PatientDto.class);
        String initialLastName = given.getLastName();
        given.setLastName("newLastName");

        PatientDto result = sut.updatePatient(given);
        result.setCurrentAdmission(null);
        result.setAdmissions(new HashSet<>());

        assertEquals(given, result);
    }
*/
    @Test
    public void getPatientShouldPassSuccess() {
        // given
        int patientId = 1;
        String patientFirstName = "PatientOnefirstname";

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
}
