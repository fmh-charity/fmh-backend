package ru.iteco.fmh.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.controller.PatientController;
import ru.iteco.fmh.dao.repository.PatientRepository;
import ru.iteco.fmh.dto.admission.AdmissionDto;
import ru.iteco.fmh.dto.note.NoteDto;
import ru.iteco.fmh.dto.patient.PatientAdmissionDto;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.model.admission.AdmissionsStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.iteco.fmh.TestUtils.*;


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

        List<PatientAdmissionDto> resultExpected = sut.getAllPatientsByStatus(List.of(AdmissionsStatus.EXPECTED.name()));
        List<PatientAdmissionDto> resultActive = sut.getAllPatientsByStatus(List.of(AdmissionsStatus.ACTIVE.name()));
        List<PatientAdmissionDto> resultDischarged = sut.getAllPatientsByStatus(List.of(AdmissionsStatus.DISCHARGED.name()));
        List<PatientAdmissionDto> resultAll = sut.getAllPatientsByStatus(List.of(AdmissionsStatus.EXPECTED.name(),
                AdmissionsStatus.ACTIVE.name(), AdmissionsStatus.DISCHARGED.name()));

        resultAll.forEach(System.out::println);

        assertAll(
                () -> assertEquals(countExpected, resultExpected.size()),
                () -> assertEquals(countActive, resultActive.size()),
                () -> assertEquals(countDischarged, resultDischarged.size()),
                () -> assertEquals(countAll, resultAll.size())
        );
    }

    @Test
    public void createOrUpdatePatientShouldPassSuccess() {
        //given
        PatientDto given = getPatientDto();
        given.setId(7);

        PatientDto result = sut.createPatient(given);

        assertAll(
                () -> assertEquals(given.getFirstName(), result.getFirstName()),
                () -> assertEquals(given.getLastName(), result.getLastName()),
                () -> assertEquals(given.getMiddleName(), result.getMiddleName()),
                () -> assertEquals(given.getBirthDate(), result.getBirthDate())
        );

       // deleting result entity
        patientRepository.deleteById(result.getId());
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
    public void getNotesShouldPassSuccess() {
        // given
        int patientId = 1;
        int notesCount =2;
        String noteDescription0 = "note1-description";
        String noteDescription1 = "note6-description";

        List<NoteDto> result = sut.getNotes(patientId);

        assertAll(
                () -> assertEquals(notesCount, result.size()),
                () -> assertEquals(noteDescription0, result.get(0).getDescription()),
                () -> assertEquals(noteDescription1, result.get(1).getDescription())
        );
    }
}
