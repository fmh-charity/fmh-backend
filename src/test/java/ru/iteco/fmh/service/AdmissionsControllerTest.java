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

import static org.junit.jupiter.api.Assertions.*;

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
        int id = 1;
        AdmissionDto given = conversionService.convert(admissionRepository.findById(id).get(), AdmissionDto.class);

        AdmissionDto result = sut.createAdmission(given);

        assertEquals(given, result);
    }

    @Test
    public void createOrUpdateAdmissionShouldPassSuccess() {
        // given
        int id = 3;
        AdmissionDto given = conversionService.convert(admissionRepository.findById(id).get(), AdmissionDto.class);
        given.setComment("new comment");

        AdmissionDto result = sut.updateAdmission(given);

        assertEquals(given, result);
    }
}
