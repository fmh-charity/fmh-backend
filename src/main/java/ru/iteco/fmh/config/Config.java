package ru.iteco.fmh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.converter.Converter;
//import ru.iteco.fmh.converter.admission.AdmissionDtoToAdmissionConverter;
//import ru.iteco.fmh.converter.admission.AdmissionToAdmissionDtoConverter;
//import ru.iteco.fmh.converter.dto.fromDto.DtoToNoteConverter;
//import ru.iteco.fmh.converter.dto.fromDto.DtoToPatientConverter;
//import ru.iteco.fmh.converter.note.NoteToDtoConverter;
//import ru.iteco.fmh.converter.note.NoteToShortDtoConverter;
//import ru.iteco.fmh.converter.patient.fromPatient.PatientToDtoConverter;
//import ru.iteco.fmh.converter.patient.fromPatient.PatientToPatientDtoConverter;
//import ru.iteco.fmh.converter.patient.fromPatientDto.PatientDtoToPatientConverter;
import ru.iteco.fmh.converter.patient.fromPatient.PatientToPatientAdmissionDtoConverter;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class Config {
    @Bean()
    public ConversionServiceFactoryBean conversionServiceFactoryBean() {
        ConversionServiceFactoryBean factoryBean = new ConversionServiceFactoryBean();
        Set<Converter> converterSet = new HashSet<>();
//        DtoToPatientConverter dtoToPatientConverter = new DtoToPatientConverter();
//        converterSet.add(dtoToPatientConverter);
//        converterSet.add(new PatientToDtoConverter());
//        converterSet.add(new NoteToDtoConverter(new PatientToDtoConverter()));
//        converterSet.add(new DtoToNoteConverter(dtoToPatientConverter));
//        converterSet.add(new NoteToShortDtoConverter());
//        PatientToPatientDtoConverter patientToPatientDtoConverter = new PatientToPatientDtoConverter();
//        PatientDtoToPatientConverter patientDtoToPatientConverter = new PatientDtoToPatientConverter();
//        converterSet.add(patientToPatientDtoConverter);
//        converterSet.add(patientDtoToPatientConverter);
//        converterSet.add(new AdmissionDtoToAdmissionConverter(patientDtoToPatientConverter));
//        converterSet.add(new AdmissionToAdmissionDtoConverter(patientToPatientDtoConverter));

        // УДАЛИТЬ!
//        Admission admission = new Admission();
//        converterSet.add(new PatientToPatientAdmissionDtoConverter(admission));

        converterSet.add(new PatientToPatientAdmissionDtoConverter());

        factoryBean.setConverters(converterSet);

        return factoryBean;
    }
}
