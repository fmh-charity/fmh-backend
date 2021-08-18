package ru.iteco.fmh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.converter.Converter;
import ru.iteco.fmh.converter.admission.AdmissionDtoToAdmissionConverter;
import ru.iteco.fmh.converter.admission.AdmissionToAdmissionDtoConverter;

import ru.iteco.fmh.converter.claim.fromClaim.ClaimToClaimDtoConverter;
//import ru.iteco.fmh.converter.claim.fromClaim.ClaimToClaimShortInfoDtoConverter;
import ru.iteco.fmh.converter.claim.fromClaimDto.ClaimDtoToClaimConverter;
import ru.iteco.fmh.converter.wish.fromWish.WishToWishDtoConverter;
import ru.iteco.fmh.converter.wish.fromWish.WishToWishShortDtoConverter;
import ru.iteco.fmh.converter.wish.fromWishDto.WishDtoToWishConverter;
import ru.iteco.fmh.converter.patient.fromPatient.PatientToPatientAdmissionDtoConverter;
import ru.iteco.fmh.converter.patient.fromPatient.PatientToPatientDtoConverter;
import ru.iteco.fmh.converter.patient.fromPatientDto.PatientDtoToPatientConverter;
import ru.iteco.fmh.converter.user.fromUser.UserToUserDtoConverter;
import ru.iteco.fmh.converter.user.fromUserDto.UserDtoToUserConverter;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class Config {
    @Bean()
    public ConversionServiceFactoryBean conversionServiceFactoryBean() {
        ConversionServiceFactoryBean factoryBean = new ConversionServiceFactoryBean();
        Set<Converter> converterSet = new HashSet<>();
        PatientToPatientDtoConverter patientToPatientDtoConverter = new PatientToPatientDtoConverter();
        PatientDtoToPatientConverter patientDtoToPatientConverter = new PatientDtoToPatientConverter();

        converterSet.add(patientToPatientDtoConverter);
        converterSet.add(patientDtoToPatientConverter);
        converterSet.add(new PatientToPatientAdmissionDtoConverter());

        converterSet.add(new UserToUserDtoConverter());
        converterSet.add(new WishToWishDtoConverter(new PatientToPatientDtoConverter(), new UserToUserDtoConverter()));
        converterSet.add(new WishDtoToWishConverter(patientDtoToPatientConverter, new UserDtoToUserConverter()));
        converterSet.add(new WishToWishShortDtoConverter());

        converterSet.add(new AdmissionDtoToAdmissionConverter(patientDtoToPatientConverter));
        converterSet.add(new AdmissionToAdmissionDtoConverter(patientToPatientDtoConverter));

        converterSet.add(new ClaimToClaimDtoConverter(new UserToUserDtoConverter()));
//        converterSet.add(new ClaimToClaimShortInfoDtoConverter());
        converterSet.add(new ClaimDtoToClaimConverter(new UserDtoToUserConverter()));

        factoryBean.setConverters(converterSet);
        return factoryBean;
    }
}
