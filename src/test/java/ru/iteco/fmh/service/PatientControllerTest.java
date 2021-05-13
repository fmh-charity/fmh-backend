package ru.iteco.fmh.service;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.controller.PatientController;
import ru.iteco.fmh.dto.admission.AdmissionDto;
import ru.iteco.fmh.dto.note.NoteDto;
import ru.iteco.fmh.dto.patient.PatientAdmissionDto;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.model.admission.AdmissionsStatus;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.iteco.fmh.TestUtils.*;

// ТЕСТЫ ЗАВЯЗАНЫ НА ТЕСТОВЫЕ ДАННЫЕ В БД!!

// тест метода createPatient добавляет запись в БД, поэтому задается порядок выполнения
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

@RunWith(SpringRunner.class)
@SpringBootTest()
public class PatientControllerTest {
    @Autowired
    PatientController sut;


    @Test
    public void aGetAllPatientsByStatusTestShouldPassSuccess() {
        List<PatientAdmissionDto> resultExpected = sut.getAllPatientsByStatus(List.of(AdmissionsStatus.EXPECTED.name()));
        List<PatientAdmissionDto> resultActive = sut.getAllPatientsByStatus(List.of(AdmissionsStatus.ACTIVE.name()));
        List<PatientAdmissionDto> resultDischarged = sut.getAllPatientsByStatus(List.of(AdmissionsStatus.DISCHARGED.name()));
        List<PatientAdmissionDto> resultAll = sut.getAllPatientsByStatus(List.of(AdmissionsStatus.EXPECTED.name(),
                AdmissionsStatus.ACTIVE.name(), AdmissionsStatus.DISCHARGED.name()));

        assertAll(
                ()-> assertEquals(3, resultExpected.size()),
                ()-> assertEquals(2, resultActive.size()),
                ()-> assertEquals(1, resultDischarged.size()),
                ()-> assertEquals(6, resultAll.size())
        );
    }

    @Test
    public void createOrUpdatePatientShouldPassSuccess() {
        PatientDto given = getPatientDto();

        PatientDto result = sut.createPatient(given);

        assertAll(
                ()->   assertEquals(given.getFirstName(), result.getFirstName()),
                ()->   assertEquals(given.getLastName(), result.getLastName()),
                ()->   assertEquals(given.getMiddleName(), result.getMiddleName()),
                ()->   assertEquals(given.getBirthDate(), result.getBirthDate())
        );
    }


    @Test
    public void getPatientShouldPassSuccess() {
        // given
        String PATIENT_NAME = "Patient1-firstname";

        PatientDto result = sut.getPatient(1);

        assertEquals(PATIENT_NAME, result.getFirstName());
    }


    @Test
    public void getAdmissionsShouldPassSuccess() {
        // given
        String ADMISSION1_COMMENT = "admission6-comment";
        String ADMISSION2_COMMENT = "admission7-comment";

        List<AdmissionDto> result = sut.getAdmissions(6);

        assertAll(
                () -> assertEquals(2, result.size()),
                () -> assertEquals(ADMISSION1_COMMENT, result.get(0).getComment()),
                () -> assertEquals(ADMISSION2_COMMENT, result.get(1).getComment())
        );
    }

    @Test
    public void getNotesShouldPassSuccess() {
        // given
        String NOTE1_DESCRIPTION = "note1-description";
        String NOTE2_DESCRIPTION = "note6-description";

        List<NoteDto> result = sut.getNotes(1);

        assertAll(
                () -> assertEquals(2, result.size()),
                () -> assertEquals(NOTE1_DESCRIPTION, result.get(0).getDescription()),
                () -> assertEquals(NOTE2_DESCRIPTION, result.get(1).getDescription())
        );
    }
}
