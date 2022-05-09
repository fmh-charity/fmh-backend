package ru.iteco.fmh.converter;

import org.junit.jupiter.api.Test;
import ru.iteco.fmh.converter.admission.AdmissionToAdmissionDtoConverter;
import ru.iteco.fmh.dto.admission.AdmissionDto;
import ru.iteco.fmh.model.admission.Admission;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static ru.iteco.fmh.TestUtils.getAdmission;


public class AdmissionToAdmissionDtoConverterTest {
    AdmissionToAdmissionDtoConverter sut = new AdmissionToAdmissionDtoConverter();

    @Test
    void convertShouldPassSuccess() {
        // given
        Admission admission = getAdmission();
        AdmissionDto admissionDto = sut.convert(admission);
        assertAll(
                () -> assertEquals(admission.getId(), admissionDto.getId()),
                () -> assertEquals(admission.getPatient().getId(), admissionDto.getPatientId()),
                () -> assertEquals(admission.getPlanDateIn().toEpochMilli(), admissionDto.getPlanDateIn()),
                () -> assertNull(admission.getPlanDateOut()),
                () -> assertNull(admissionDto.getPlanDateOut()),
                () -> assertNull(admission.getFactDateIn()),
                () -> assertNull(admissionDto.getFactDateIn()),
                () -> assertNull(admission.getFactDateOut()),
                () -> assertNull(admissionDto.getFactDateOut()),
                () -> assertEquals(admission.getStatus(), admissionDto.getStatus()),
                () -> assertEquals(admission.getRoom().getId(), admissionDto.getRoomId()),
                () -> assertEquals(admission.getComment(), admissionDto.getComment())
        );
    }
}
