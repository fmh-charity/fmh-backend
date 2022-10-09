package ru.iteco.fmh.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.controller.AdmissionsController;
import ru.iteco.fmh.dao.repository.AdmissionRepository;
import ru.iteco.fmh.dto.admission.AdmissionDto;
import ru.iteco.fmh.model.admission.Admission;
import ru.iteco.fmh.service.admission.AdmissionService;

import static org.junit.jupiter.api.Assertions.*;
import static ru.iteco.fmh.TestUtils.*;

// ТЕСТЫ ЗАВЯЗАНЫ НА ТЕСТОВЫЕ ДАННЫЕ В БД!!
@RunWith(SpringRunner.class)
@SpringBootTest()
public class AdmissionsControllerTest {
    @Autowired
    AdmissionsController sut;

    @Autowired
    AdmissionRepository admissionRepository;

    @Autowired
    ConversionService conversionService;

    @Autowired
    AdmissionService admissionService;

    @Test
    public void getAdmissionShouldPassSuccess() {
        // given
        int admissionId = 1;

        AdmissionDto expected = conversionService.convert(admissionRepository.findById(admissionId).get(), AdmissionDto.class);

        AdmissionDto result = sut.getAdmission(admissionId);

        assertEquals(expected, result);
    }

    @Test
    public void createAdmissionShouldPassSuccess() {
        // given
        AdmissionDto givenDto = getAdmissionDto();
        givenDto.setId(0);
        givenDto.setPatientId(1);
        givenDto.setRoomId(1);

        AdmissionDto resultDto = sut.createAdmission(givenDto);

        Integer resultId = resultDto.getId();

        assertNotNull(resultId);

        givenDto.setId(resultId);
        assertEquals(givenDto, resultDto);

        // After

        admissionService.deleteAdmissionById(resultId);
    }

    @Test
    public void updateAdmissionShouldPassSuccess() {
        // given
        int id = 1;
        AdmissionDto givenDto = conversionService.convert(admissionRepository.findById(id).get(), AdmissionDto.class);
        String initialComment = givenDto.getComment();
        givenDto.setComment("new comment");

        AdmissionDto result = sut.updateAdmission(givenDto);

        assertEquals(givenDto, result);

        // After
        result.setComment(initialComment);
        admissionRepository.save(conversionService.convert(result, Admission.class));
    }
}
