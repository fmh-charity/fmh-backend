package ru.iteco.fmh.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.controller.PatientController;
import ru.iteco.fmh.dao.repository.PatientRepository;
import ru.iteco.fmh.dto.admission.AdmissionDto;
import ru.iteco.fmh.dto.wish.WishDto;
import ru.iteco.fmh.dto.patient.PatientAdmissionDto;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.model.Patient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.iteco.fmh.TestUtils.*;
import static ru.iteco.fmh.model.admission.AdmissionsStatus.*;


// ТЕСТЫ ЗАВЯЗАНЫ НА ТЕСТОВЫЕ ДАННЫЕ В БД!!
@RunWith(SpringRunner.class)
@SpringBootTest()
public class PatientControllerTest {
    @Autowired
    PatientController sut;

    @Autowired
    PatientRepository patientRepository;
    @Autowired
    ConversionServiceFactoryBean factoryBean;

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
        PatientDto given = getPatientDto();

        Integer id = sut.createPatient(given);

        assertNotNull(id);

        Patient result = patientRepository.findById(id).get();

        assertAll(
                () -> assertEquals(given.getFirstName(), result.getFirstName()),
                () -> assertEquals(given.getMiddleName(), result.getMiddleName()),
                () -> assertEquals(given.getLastName(), result.getLastName()),
                () -> assertEquals(given.getBirthDate(), result.getBirthDate())
        );

        // deleting result entity
        patientRepository.deleteById(id);
    }

    @Test
    public void updatePatientShouldPassSuccess() {
        ConversionService conversionService = factoryBean.getObject();

        // given
        int patientId = 1;
        PatientDto given = conversionService.convert(patientRepository.findById(patientId).get(), PatientDto.class);
        String newLastName = "new lastName";

        assertNotEquals(given.getLastName(), newLastName);

        given.setLastName(newLastName);

        PatientDto result = sut.updatePatient(given);

        assertEquals(given.getLastName(), result.getLastName());

        //after
        result.setLastName("Patient1-lastname");
        patientRepository.save(conversionService.convert(result,Patient.class));
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

    // TODO: отрефакторить тест 17.08
//    @Test
//    public void getNotesShouldPassSuccess() {
//        // given
//        int patientId = 1;
//        int notesCount = 2;
//        String noteDescription0 = "note1-description";
//        String noteDescription1 = "note6-description";
//
//        List<WishDto> notes = sut.getNotes(patientId);
//        List<String> result = List.of(notes.get(0).getDescription(), notes.get(1).getDescription());
//
//        assertAll(
//                () -> assertEquals(notesCount, notes.size()),
//                () -> assertTrue(result.contains(noteDescription0)),
//                () -> assertTrue(result.contains(noteDescription1))
//        );
//    }
}
