package ru.iteco.fmh.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.iteco.fmh.converter.admission.AdmissionToAdmissionDtoConverter;
import ru.iteco.fmh.converter.patient.PatientToPatientDtoConverter;
import ru.iteco.fmh.dto.admission.AdmissionDto;
import ru.iteco.fmh.model.admission.Admission;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.iteco.fmh.TestUtils.getAdmission;


public class AdmissionToAdmissionDtoConverterTest {

    PatientToPatientDtoConverter patientToDtoConverter = new PatientToPatientDtoConverter();

    AdmissionToAdmissionDtoConverter convertor = new AdmissionToAdmissionDtoConverter(patientToDtoConverter);

    @Test
    void convert() {

        Admission admission = getAdmission();

        AdmissionDto admissionDto = convertor.convert(admission);
        Assertions.assertAll(
                () -> assertEquals(admission.getId(), admissionDto.getId()),
                () -> assertEquals(patientToDtoConverter.convert(admission.getPatient()), admissionDto.getPatient()),
                () -> assertEquals(admission.getComment(), admissionDto.getComment()),
                () -> assertEquals(admission.getPlanDateIn(), admissionDto.getPlanDateIn()),
                () ->  assertEquals(admission.getFactDateIn(),admissionDto.getFactDateIn()),
                () ->  assertEquals(admission.getFactDateOut(),admissionDto.getFactDateOut())
        );

    }

}
