package ru.iteco.fmh.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.controller.PatientsController;
import ru.iteco.fmh.dao.repository.PatientRepository;
import ru.iteco.fmh.dto.patient.PatientByStatusRs;
import ru.iteco.fmh.dto.patient.PatientCreateInfoDtoRq;
import ru.iteco.fmh.dto.patient.PatientCreateInfoDtoRs;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.dto.patient.PatientUpdateInfoDtoRq;
import ru.iteco.fmh.dto.patient.PatientUpdateInfoDtoRs;
import ru.iteco.fmh.dto.wish.WishDto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.iteco.fmh.TestUtils.getPatientCreateInfoDtoRq;
import static ru.iteco.fmh.model.PatientStatus.ACTIVE;
import static ru.iteco.fmh.model.PatientStatus.DISCHARGED;
import static ru.iteco.fmh.model.PatientStatus.EXPECTED;


// ТЕСТЫ ЗАВЯЗАНЫ НА ТЕСТОВЫЕ ДАННЫЕ В БД!!
@RunWith(SpringRunner.class)
@SpringBootTest()
@WithMockUser(username = "login1", password = "password1", roles = "ADMINISTRATOR")
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

        List<PatientByStatusRs> resultExpected = sut.getAllPatientsByStatus(List.of(EXPECTED.name()));
        List<PatientByStatusRs> resultActive = sut.getAllPatientsByStatus(List.of(ACTIVE.name()));
        List<PatientByStatusRs> resultDischarged = sut.getAllPatientsByStatus(List.of(DISCHARGED.name()));
        List<PatientByStatusRs> resultAll = sut.getAllPatientsByStatus(List.of(EXPECTED.name(),
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
        patientRepository.deleteById(resultDto.getId());
    }

    @Test
    public void updatePatientShouldPassSuccess() {
        PatientCreateInfoDtoRq givenDto = getPatientCreateInfoDtoRq();

        PatientCreateInfoDtoRs resultDto = sut.createPatient(givenDto);
        // given
        int patientId = resultDto.getId();
        PatientUpdateInfoDtoRq given = PatientUpdateInfoDtoRq.builder().firstName("Test").lastName("Test")
                .middleName("-").birthDate(LocalDate.now()).status(DISCHARGED).build();
        PatientUpdateInfoDtoRs result = sut.updatePatient(given, patientId);
        assertAll(
                () -> assertEquals(given.getFirstName(), result.getFirstName()),
                () -> assertEquals(given.getLastName(), result.getLastName()),
                () -> assertEquals(given.getMiddleName(), result.getMiddleName()),
                () -> assertEquals(given.getBirthDate(), result.getBirthDate()),
                () -> assertEquals(patientId, result.getId())
        );
        patientRepository.deleteById(patientId);
    }

    @Test
    public void getPatientShouldPassSuccess() {
        // given
        int patientId = 1;
        String patientFirstName = "PatientOnefirstname";

        PatientDto result = sut.getPatient(patientId);

        assertEquals(patientFirstName, result.getFirstName());
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
