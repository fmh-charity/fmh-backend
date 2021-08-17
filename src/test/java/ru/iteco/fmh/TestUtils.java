package ru.iteco.fmh;

import ru.iteco.fmh.dto.claim.ClaimDto;
import ru.iteco.fmh.dto.wish.WishDto;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.dto.user.UserDto;
import ru.iteco.fmh.model.*;
import ru.iteco.fmh.model.admission.Admission;
import ru.iteco.fmh.model.admission.AdmissionsStatus;
import ru.iteco.fmh.model.task.StatusE;
import ru.iteco.fmh.model.task.claim.Claim;
import ru.iteco.fmh.model.user.User;
import ru.iteco.fmh.model.task.wish.Wish;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

public class TestUtils {
    public static String getAlphabeticStringR() {
        return getAlphabeticString(10);
    }

    public static String getAlphabeticString(int targetStringLength) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public static String getNumeric() {
        return getNumeric(10);
    }

    public static String getNumeric(int targetStringLength) {
        int leftLimit = 48; // letter 'a'
        int rightLimit = 57; // letter 'z'
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public static Wish getNote() {

        return Wish.builder()
                .id(Integer.valueOf(getNumeric(2)))
                .patient(getPatient())
                .creator(getUser())
                .executor(getUser())
                .description(getAlphabeticStringR())
                .createDate(LocalDateTime.now())
                .planExecuteDate(LocalDateTime.now())
                .factExecuteDate(LocalDateTime.now())
                .status(StatusE.OPEN)
                .build();
    }


    public static Patient getPatient() {
        Patient patient = Patient.builder()
                .id(Integer.valueOf(getNumeric(2)))
                .firstName(getAlphabeticStringR())
                .lastName(getAlphabeticStringR())
                .middleName(getAlphabeticStringR())
                .birthDate(LocalDate.now())
                .currentAdmission(Admission.builder().build())
                .build();
        String shortName = getShortName(patient.getFirstName(), patient.getLastName(), patient.getMiddleName());
        patient.setShortPatientName(shortName);
        return patient;
    }

    public static String getShortName(String firstName, String lastname, String middleName) {
        String firstLetterOfName = Character.toString(firstName.charAt(0));
        String firstLetterOfMiddleName = Character.toString(middleName.charAt(0));
        return String.format("%s %s.%s.", lastname, firstLetterOfName, firstLetterOfMiddleName);
    }

    public static UserDto getUserDto() {
        UserDto userDto = UserDto.builder()
                .id(Integer.valueOf(getNumeric(2)))
                .firstName(getAlphabeticStringR())
                .lastName(getAlphabeticStringR())
                .middleName(getAlphabeticStringR())
                .login(getAlphabeticStringR())
                .password(getAlphabeticStringR())
                .phoneNumber(getAlphabeticStringR())
                .build();
        String shortName = getShortName(userDto.getFirstName(), userDto.getLastName(), userDto.getMiddleName());
        userDto.setShortUserName(shortName);
        return userDto;

    }

    public static User getUser() {
        User user = User.builder()
                .id(Integer.valueOf(getNumeric(2)))
                .login(getAlphabeticStringR())
                .password(getAlphabeticStringR())
                .firstName(getAlphabeticStringR())
                .lastName(getAlphabeticStringR())
                .middleName(getAlphabeticStringR())
                .phoneNumber(getAlphabeticStringR())
                .email(getAlphabeticStringR())
                .build();
        String shortName = getShortName(user.getFirstName(), user.getLastName(), user.getMiddleName());
        user.setShortUserName(shortName);
        return user;

    }


    public static WishDto getNoteDto() {

        return WishDto.builder()
                .patient(getPatientDto())
                .description(getAlphabeticStringR())
                .planExecuteDate(LocalDateTime.now().withNano(0))
                .createDate(LocalDateTime.now().withNano(0))
                .factExecuteDate(null)
                .executor(getUserDto())
                .creator(getUserDto())
                .status(StatusE.OPEN)

                .build();
    }

    public static PatientDto getPatientDto() {
        PatientDto patientDto = PatientDto.builder()
                .id(Integer.valueOf(getNumeric(2)))
                .firstName(getAlphabeticStringR())
                .lastName(getAlphabeticStringR())
                .middleName(getAlphabeticStringR())
                .birthDate(LocalDate.now())
                .build();
        String shortName = getShortName(patientDto.getFirstName(), patientDto.getLastName(), patientDto.getMiddleName());
        patientDto.setShortPatientName(shortName);
        return patientDto;
    }

    public static Admission getAdmission() {
        return Admission.builder()
                .id(Integer.valueOf(getNumeric(1)))
                .patient(getPatient())
                .planDateIn(null)
                .planDateOut(null)
                .factDateIn(LocalDateTime.now())
                .factDateOut(null)
                .status(AdmissionsStatus.ACTIVE)
                .room(new Room())
                .comment(getAlphabeticStringR())
                .build();
    }
    public static Claim getClaim() {

        return Claim.builder()
                .id(Integer.valueOf(getNumeric(2)))
                .creator(getUser())
                .executor(getUser())
                .description(getAlphabeticStringR())
                .createDate(LocalDateTime.now())
                .planExecuteDate(LocalDateTime.now())
                .factExecuteDate(LocalDateTime.now())
                .status(StatusE.OPEN)
                .build();
    }

    public static ClaimDto getClaimDto() {

        return ClaimDto.builder()
                .description(getAlphabeticStringR())
                .planExecuteDate(LocalDateTime.now().withNano(0))
                .createDate(LocalDateTime.now().plusDays(2).withNano(0))
                .factExecuteDate(null)
                .executor(getUserDto())
                .creator(getUserDto())
                .status(StatusE.OPEN)
                .build();
    }

}
