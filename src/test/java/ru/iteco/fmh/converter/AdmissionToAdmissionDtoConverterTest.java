package ru.iteco.fmh.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.iteco.fmh.converter.admission.AdmissionToAdmissionDtoConverter;
import ru.iteco.fmh.converter.patient.fromPatient.IPatientToPatientDtoConverter;
import ru.iteco.fmh.dto.admission.AdmissionDto;
import ru.iteco.fmh.model.admission.Admission;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static ru.iteco.fmh.TestUtils.getAdmission;


public class AdmissionToAdmissionDtoConverterTest {

    IPatientToPatientDtoConverter patientToDtoConverter = Mockito.mock(IPatientToPatientDtoConverter.class);

    AdmissionToAdmissionDtoConverter convertor = new AdmissionToAdmissionDtoConverter(patientToDtoConverter);

    @Test
    void convert() {
        when(patientToDtoConverter.convert(any())).thenReturn(null);

        Admission admission = getAdmission();

        AdmissionDto admissionDto = convertor.convert(admission);
        Assertions.assertAll(
                () -> assertEquals(admission.getId(), admissionDto.getId()),
                () -> assertEquals(admission.getPatient(), admissionDto.getPatient()),
                () -> assertEquals(admission.getComment(), admissionDto.getComment()),
                () -> assertEquals(admission.getPlanDateIn(), admissionDto.getPlanDateIn()),
                () ->  assertEquals(admission.getFactDateIn(),admissionDto.getFactDateIn()),
                () ->  assertEquals(admission.getFactDateOut(),admissionDto.getFactDateOut())
        );

    }

}
