package ru.iteco.fmh.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.dao.repository.PatientRepository;
import ru.iteco.fmh.dto.patient.*;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.PatientStatus;
import ru.iteco.fmh.service.patient.PatientService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.iteco.fmh.TestUtils.*;
import static ru.iteco.fmh.model.PatientStatus.*;


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
        int pages =0;
        int elements =2;
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        PageRequest pageRequest =PageRequest.of(pages, elements, sort);

        List<PatientByStatusRs> result = sut.getAllPatientsByStatus(pageRequest,"OnefirstName");

        assertEquals(1,result.size());

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
