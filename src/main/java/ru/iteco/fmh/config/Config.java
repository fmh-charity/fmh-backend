package ru.iteco.fmh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.converter.Converter;
import ru.iteco.fmh.converter.*;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class Config {
    @Bean()
    public ConversionServiceFactoryBean conversionServiceFactoryBean() {
        ConversionServiceFactoryBean factoryBean = new ConversionServiceFactoryBean();
        Set<Converter> converterSet = new HashSet<>();
        DtoToPatientConverter dtoToPatientConverter = new DtoToPatientConverter();
        converterSet.add(dtoToPatientConverter);
        converterSet.add(new PatientToDtoConverter());
        converterSet.add(new NoteToDtoConverter(new PatientToDtoConverter()));
        converterSet.add(new DtoToNoteConverter(dtoToPatientConverter));
        converterSet.add(new NoteToShortDtoConverter());
        PatientToPatientDtoConverter patientToPatientDtoConverter = new PatientToPatientDtoConverter();
        PatientDtoToPatientConverter patientDtoToPatientConverter = new PatientDtoToPatientConverter();
        converterSet.add(patientToPatientDtoConverter);
        converterSet.add(patientDtoToPatientConverter);
        converterSet.add(new AdmissionDtoToAdmissionConverter(patientDtoToPatientConverter));
        converterSet.add(new AdmissionToAdmissionDtoConverter(patientToPatientDtoConverter));
        factoryBean.setConverters(converterSet);

        return factoryBean;
    }
}
