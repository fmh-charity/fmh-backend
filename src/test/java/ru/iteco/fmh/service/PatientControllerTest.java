package ru.iteco.fmh.service;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.controller.PatientController;
import ru.iteco.fmh.dao.repository.AdmissionRepository;
import ru.iteco.fmh.dao.repository.NoteRepository;
import ru.iteco.fmh.dao.repository.PatientRepository;
import ru.iteco.fmh.dto.patient.PatientAdmissionDto;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.admission.Admission;
import ru.iteco.fmh.model.admission.AdmissionsStatus;
import ru.iteco.fmh.service.admission.AdmissionService;
import ru.iteco.fmh.service.note.NoteService;
import ru.iteco.fmh.service.patient.PatientService;
import ru.iteco.fmh.testentity.PatientAdmissionDtoList;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.iteco.fmh.TestUtils.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PatientControllerTest {
    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    PatientController patientController;

    @Autowired
    PatientRepository patientRepository;
    @Autowired
    AdmissionRepository admissionRepository;
    @Autowired
    NoteRepository noteRepository;

    // defined не читается из проперти...
    @LocalServerPort
    int randomServerPort;

    @AfterEach
    public void resetDb() {
        patientRepository.deleteAll();
        admissionRepository.deleteAll();
        noteRepository.deleteAll();
    }

    @BeforeEach
    public void initDb() {

    }

    @Test
    public void probeTest() {
        PatientDto patientDto = new PatientDto();

        PatientDto patient = patientController.createPatient(patientDto);
        System.out.println(patient);
    }



























//    @Test
//    public void createPatientShouldPassSuccess() {
//        // given
//        PatientDto given = getPatientDto();
//
//        PatientDto result = restTemplate.postForObject(getUri("/create"), given, PatientDto.class);
//
//        assertEquals(given,result);
//    }
//
//    @Test
//    public void getAllPatientsByStatusShouldPassSuccess() {
//       // given
//        List<String> given = List.of(AdmissionsStatus.EXPECTED.name(), AdmissionsStatus.DISCHARGED.name());
//
//        PatientAdmissionDtoList resultWrapper = restTemplate.getForObject(getUri("/getAll"), PatientAdmissionDtoList.class, given);
//
//        List<PatientAdmissionDto> result = resultWrapper.getPatientAdmissionDtoList();
//
//        // TODO:
//        int expectedAdmissionsCount = 0;
//
//        assertEquals(expectedAdmissionsCount, result.size());
//    }


    private String getUri(String endpoint) {
        return "http://localhost:" + randomServerPort + "/fmh/patient" + endpoint;
    }


//    @ApiOperation(value = "реестр всех пациентов с учетом пагинации")
//    @GetMapping
//    public List<PatientAdmissionDto> getAllPatientsByStatus(
//            @ApiParam(value = "начальная позиция пагинации", required = true) @RequestParam("offset") Integer offset,
//            @ApiParam(value = "конечная позиция пагинации", required = true) @RequestParam("limit") Integer limit,
//            @ApiParam(value = "список статусов для отображения") @RequestParam("patients_status_list") List<String> patientsStatusList
//    ) {
//        return patientService.getAllPatientsByStatus(patientsStatusList);
//    }

}
