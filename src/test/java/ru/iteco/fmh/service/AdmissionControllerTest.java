package ru.iteco.fmh.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.test.context.junit4.SpringRunner;
import ru.iteco.fmh.controller.AdmissionController;
import ru.iteco.fmh.dao.repository.AdmissionRepository;
import ru.iteco.fmh.dao.repository.PatientRepository;
import ru.iteco.fmh.dao.repository.RoomRepository;
import ru.iteco.fmh.dto.admission.AdmissionDto;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.model.admission.Admission;
import ru.iteco.fmh.model.admission.AdmissionsStatus;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

// ТЕСТЫ ЗАВЯЗАНЫ НА ТЕСТОВЫЕ ДАННЫЕ В БД!!
@RunWith(SpringRunner.class)
@SpringBootTest()
public class AdmissionControllerTest {
    @Autowired
    AdmissionController sut;

    @Autowired
    AdmissionRepository admissionRepository;
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    PatientRepository patientRepository;

    @Autowired
    ConversionServiceFactoryBean factoryBean;

    @Test
    public void getAdmissionShouldPassSuccess() {
        ConversionService conversionService = factoryBean.getObject();

        // given
        int admissionId = 1;
        AdmissionDto given = conversionService.convert(admissionRepository.findById(admissionId).get(), AdmissionDto.class);

        AdmissionDto result = sut.getAdmission(admissionId);

        assertAll(
                () -> assertEquals(given.getId(), result.getId()),
                () -> assertEquals(given.getPatient(), result.getPatient()),
                () -> assertEquals(given.getRoom().getId(), result.getRoom().getId()),
                () -> assertEquals(given.getRoom().getName(), result.getRoom().getName()),
                () -> assertEquals(given.getRoom().getComment(), result.getRoom().getComment()),
                () -> assertEquals(given.getRoom().getMaxOccupancy(), result.getRoom().getMaxOccupancy()),
                () -> assertEquals(given.getRoom().getBlock().getId(), result.getRoom().getBlock().getId()),
                () -> assertEquals(given.getRoom().getBlock().getName(), result.getRoom().getBlock().getName()),
                () -> assertEquals(given.getRoom().getBlock().getComment(), result.getRoom().getBlock().getComment()),
                () -> assertEquals(given.getRoom().getNurseStation().getId(), result.getRoom().getNurseStation().getId()),
                () -> assertEquals(given.getRoom().getNurseStation().getName(), result.getRoom().getNurseStation().getName()),
                () -> assertEquals(given.getRoom().getNurseStation().getComment(), result.getRoom().getNurseStation().getComment()),
                () -> assertEquals(given.getStatus(), result.getStatus())
        );
    }

    @Test
    public void createAdmissionShouldPassSuccess() {
        ConversionService conversionService = factoryBean.getObject();

        // given
        AdmissionDto given = getAdmissionDto();
        given.setPatient(conversionService.convert(patientRepository.findById(1).get(), PatientDto.class));
        given.setRoom(roomRepository.findById(1).get());

        Integer id = sut.createAdmission(given);

        assertNotNull(id);

        Admission result = admissionRepository.findById(id).get();

        assertAll(
                () -> assertEquals(given.getPatient(), conversionService.convert(result.getPatient(), PatientDto.class)),
                () -> assertEquals(given.getStatus(), result.getStatus()),
                () -> assertEquals(given.getComment(), result.getComment()),
                () -> assertEquals(given.getRoom().getId(), result.getRoom().getId()),
                () -> assertEquals(given.getRoom().getName(), result.getRoom().getName()),
                () -> assertEquals(given.getRoom().getComment(), result.getRoom().getComment()),
                () -> assertEquals(given.getRoom().getMaxOccupancy(), result.getRoom().getMaxOccupancy()),
                () -> assertEquals(given.getRoom().getBlock().getId(), result.getRoom().getBlock().getId()),
                () -> assertEquals(given.getRoom().getBlock().getName(), result.getRoom().getBlock().getName()),
                () -> assertEquals(given.getRoom().getBlock().getComment(), result.getRoom().getBlock().getComment()),
                () -> assertEquals(given.getRoom().getNurseStation().getId(), result.getRoom().getNurseStation().getId()),
                () -> assertEquals(given.getRoom().getNurseStation().getName(), result.getRoom().getNurseStation().getName()),
                () -> assertEquals(given.getRoom().getNurseStation().getComment(), result.getRoom().getNurseStation().getComment()),
                () -> assertEquals(given.getFactDateIn(), result.getFactDateIn()),
                () -> assertEquals(given.getFactDateOut(), result.getFactDateOut()),
                () -> assertEquals(given.getPlanDateIn(), result.getPlanDateIn()),
                () -> assertEquals(given.getPlanDateOut(), result.getPlanDateOut())
        );

        // deleting result entity
        admissionRepository.deleteById(id);
    }

    @Test
    public void updateAdmissionShouldPassSuccess() {
        ConversionService conversionService = factoryBean.getObject();

        // given
        int id = 3;
        AdmissionDto given = conversionService.convert(admissionRepository.findById(id).get(), AdmissionDto.class);
        String newComment = "new comment";

        assertNotEquals(given.getComment(), newComment);

        given.setComment(newComment);

        AdmissionDto result = sut.updateAdmission(given);

        assertEquals(given.getComment(), result.getComment());
    }

    public static AdmissionDto getAdmissionDto() {
        return AdmissionDto.builder()
                .planDateIn(LocalDate.now())
                .planDateOut(LocalDate.now())
                .factDateIn(LocalDate.now())
                .factDateOut(LocalDate.now())
                .status(AdmissionsStatus.ACTIVE)
                .comment("comment")
                .build();
    }
}
