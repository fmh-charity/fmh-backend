package ru.iteco.fmh.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import extensions.ClearDatabase;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.iteco.fmh.controller.PatientsController;
import ru.iteco.fmh.dao.repository.PatientRepository;
import ru.iteco.fmh.dto.patient.*;
import ru.iteco.fmh.dto.wish.WishDto;
import ru.iteco.fmh.service.patient.PatientService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.iteco.fmh.TestUtils.getPatientCreateInfoDtoRq;
import static ru.iteco.fmh.model.PatientStatus.DISCHARGED;

// ТЕСТЫ ЗАВЯЗАНЫ НА ТЕСТОВЫЕ ДАННЫЕ В БД!!

@SpringBootTest()
@ClearDatabase
@WithMockUser(username = "login1", password = "password1", roles = "ADMINISTRATOR")
public class PatientsControllerTest {
    @Autowired
    PatientsController sut;


    @Autowired
    PatientRepository patientRepository;
    @Autowired
    ConversionService conversionService;
    @Mock
    private PatientService patientService;

    @Autowired
    private WebApplicationContext webApplicationContext;


    @Test
    @WithUserDetails()
    public void testGetAllPatientsByStatus() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(get("/patients")
                        .param("pages", "0")
                        .param("elements", "3")
                        .param("search", "")
                        .param("sortDirection", "ASC")
                        .param("sortField", "id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3))); // Замените эту часть кода в соответствии с ожидаемым результатом
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
