package ru.iteco.fmh;

import ru.iteco.fmh.dto.admission.AdmissionDto;
import ru.iteco.fmh.dto.claim.ClaimCommentDto;
import ru.iteco.fmh.dto.claim.ClaimDto;
import ru.iteco.fmh.dto.news.NewsCategoryDto;
import ru.iteco.fmh.dto.news.NewsDto;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.dto.patient.PatientDtoIdFio;
import ru.iteco.fmh.dto.user.UserDto;
import ru.iteco.fmh.dto.wish.WishCommentDto;
import ru.iteco.fmh.dto.wish.WishDto;
import ru.iteco.fmh.model.Block;
import ru.iteco.fmh.model.NurseStation;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.Room;
import ru.iteco.fmh.model.admission.Admission;
import ru.iteco.fmh.model.admission.AdmissionsStatus;
import ru.iteco.fmh.model.news.News;
import ru.iteco.fmh.model.news.NewsCategory;
import ru.iteco.fmh.model.task.Status;
import ru.iteco.fmh.model.task.claim.Claim;
import ru.iteco.fmh.model.task.claim.ClaimComment;
import ru.iteco.fmh.model.task.wish.Wish;
import ru.iteco.fmh.model.task.wish.WishComment;
import ru.iteco.fmh.model.user.Role;
import ru.iteco.fmh.model.user.User;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static ru.iteco.fmh.model.admission.AdmissionsStatus.ACTIVE;


public class TestUtils {
    public static String getAlphabeticString() {
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

    public static Role getRole(String roleName) {
        return Role.builder()
                .id(Integer.valueOf(getNumeric(2)))
                .name(roleName)
                .deleted(false)
                .build();
    }

    public static Wish getWish(Status status) {
        return Wish.builder()
                .id(Integer.valueOf(getNumeric(2)))
                .patient(getPatient())
                .creator(getUser())
                .executor(getUser())
                .description(getAlphabeticString())
                .createDate(Instant.now())
                .planExecuteDate(Instant.now())
                .factExecuteDate(Instant.now())
                .status(status)
                .wishRoles(List.of(getRole("ROLE_ADMINISTRATOR")))
                .build();
    }


    public static Patient getPatient() {
        Patient patient = Patient.builder()
                .id(Integer.valueOf(getNumeric(2)))
                .firstName(getAlphabeticString())
                .lastName(getAlphabeticString())
                .middleName(getAlphabeticString())
                .birthDate(Instant.now())
                .currentAdmission(Admission.builder().build())
                .admissions(new HashSet<>())
                .build();
        return patient;
    }

    public static String getShortName(String firstName, String lastname, String middleName) {
        String firstLetterOfName = Character.toString(firstName.charAt(0));
        String firstLetterOfMiddleName = Character.toString(middleName.charAt(0));
        return String.format("%s %s.%s.", lastname, firstLetterOfName, firstLetterOfMiddleName);
    }

    public static UserDto getUserDto() {
        return UserDto.builder()
                .id(Integer.valueOf(getNumeric(2)))
                .firstName(getAlphabeticString())
                .lastName(getAlphabeticString())
                .middleName(getAlphabeticString())
                .login(getAlphabeticString())
                .password(getAlphabeticString())
                .phoneNumber(getAlphabeticString())
                .build();
    }

    public static User getUser(Collection<Role> roles) {
        return User.builder()
                .id(Integer.valueOf(getNumeric(2)))
                .login(getAlphabeticString())
                .password(getAlphabeticString())
                .firstName(getAlphabeticString())
                .lastName(getAlphabeticString())
                .middleName(getAlphabeticString())
                .phoneNumber(getAlphabeticString())
                .email(getAlphabeticString())
                .userRoles((List<Role>) roles)
                .build();
    }

    public static User getUser() {
        return User.builder()
                .id(Integer.valueOf(getNumeric(2)))
                .login(getAlphabeticString())
                .password(getAlphabeticString())
                .firstName(getAlphabeticString())
                .lastName(getAlphabeticString())
                .middleName(getAlphabeticString())
                .phoneNumber(getAlphabeticString())
                .email(getAlphabeticString())
                .build();
    }

    public static WishDto getWishDto(Status status) {
        WishDto wishDto = getWishDto();
        wishDto.setStatus(status);
        return wishDto;
    }

    public static WishDto getWishDto() {

        return WishDto.builder()
                .patient(getPatientIdFio())
                .description(getAlphabeticString())
                .planExecuteDate(Instant.now().toEpochMilli())
                .createDate(Instant.now().toEpochMilli())
                .factExecuteDate(null)
                .executor(null)
                .creatorId(3)
                .status(Status.OPEN)
                .build();
    }

    public static PatientDto getPatientDto() {
        PatientDto patientDto = PatientDto.builder()
                .firstName(getAlphabeticString())
                .lastName(getAlphabeticString())
                .middleName(getAlphabeticString())
                .birthDate(Instant.now().toEpochMilli())
                .admissions(new HashSet<>())
                .build();
        return patientDto;
    }

    public static PatientDtoIdFio getPatientIdFio() {
        return new PatientDtoIdFio(
                1,
                "Patient6-firstname",
                "Patient6-middlename",
                "Patient6-lastname"
        );
    }

    public static Admission getAdmission() {
        return getAdmission(ACTIVE);
    }

    public static Admission getAdmission(AdmissionsStatus status) {
        return Admission.builder()
                .id(Integer.valueOf(getNumeric(2)))
                .patient(getPatient())
                .planDateIn(Instant.now())
                .planDateOut(null)
                .factDateIn(null)
                .factDateOut(null)
                .status(status)
                .room(getRoom())
                .comment(getAlphabeticString())
                .deleted(false)
                .build();
    }

    public static AdmissionDto getAdmissionDto() {
        return getAdmissionDto(ACTIVE);
    }

    public static AdmissionDto getAdmissionDto(AdmissionsStatus status) {
        return AdmissionDto.builder()
                .id(Integer.valueOf(getNumeric(2)))
                .patientId(Integer.valueOf(getNumeric(2)))
                .planDateIn(Instant.now().toEpochMilli())
                .planDateOut(null)
                .factDateIn(null)
                .factDateOut(null)
                .status(status)
                .roomId(Integer.valueOf(getNumeric(2)))
                .comment(getAlphabeticString())
                .build();
    }

    public static Claim getClaimInProgress() {

        return Claim.builder()
                // TODO: убрать hardcode
                .id(27)
                .title("title")
                .description("description")
                .creator(getUser())
                .executor(getUser())
                .createDate(Instant.now())
                .planExecuteDate(Instant.now())
                .factExecuteDate(null)
                .status(Status.IN_PROGRESS)
                .build();
    }

    public static Claim getClaimOpen() {

        return Claim.builder()
                // TODO: убрать hardcode
                .id(27)
                .title("title")
                .description("description")
                .creator(getUser())
                .executor(null)
                .createDate(Instant.now())
                .planExecuteDate(Instant.now())
                .factExecuteDate(null)
                .status(Status.OPEN)
                .build();
    }

    public static ClaimDto getClaimDtoOpen() {

        return ClaimDto.builder()
                // TODO: убрать hardcode
                .title("Title")
                .description("description")
                .creatorId(3)
                .executorId(null)
                .planExecuteDate(Instant.now().toEpochMilli())
                .createDate(Instant.now().plus(2, ChronoUnit.DAYS).toEpochMilli())
                .factExecuteDate(null)
                .status(Status.OPEN)
                .build();
    }

    public static ClaimDto getClaimDtoInProgress() {

        return ClaimDto.builder()
                // TODO: убрать hardcode
                .title("Title")
                .description("description")
                .creatorId(3)
                .executorId(3)
                .planExecuteDate(Instant.now().toEpochMilli())
                .createDate(Instant.now().plus(2, ChronoUnit.DAYS).toEpochMilli())
                .factExecuteDate(null)
                .status(Status.IN_PROGRESS)
                .build();
    }


    public static ClaimCommentDto getClaimCommentDto() {
        return ClaimCommentDto.builder()
                .claimId(2)
                .creatorId(2)
                .description("description")
                .createDate(Instant.now().toEpochMilli())
                .build();
    }

    public static ClaimComment getClaimComment(Claim claim) {
        return ClaimComment.builder()
                .claim(claim)
                .creator(getUser())
                .description("description")
                .createDate(Instant.now())
                .build();
    }


    public static WishComment getWishComment(Status wishStatus) {
        return WishComment.builder()
                .id(Integer.valueOf(getNumeric(2)))
                .wish(getWish(wishStatus))
                .description(getAlphabeticString())
                .creator(getUser())
                .createDate(Instant.now())
                .build();
    }

    public static WishCommentDto getWishCommentDto(Status wishStatus) {
        return WishCommentDto.builder()
                .id(Integer.valueOf(getNumeric(2)))
                .wishId(1)
                .description(getAlphabeticString())
                .creatorId(3)
                .createDate(Instant.now().toEpochMilli())
                .build();
    }


    public static News getNews() {
        return News.builder()
                .id(Integer.valueOf(getNumeric(2)))
                .creator(getUser())
                .newsCategory(getNewsCategory())
                .createDate(Instant.now())
                .title(getAlphabeticString())
                .description(getAlphabeticString())
                .publishDate(Instant.now())
                .publishEnabled(true)
                .build();
    }

    public static News getNews(Instant publishDate) {
        News news = getNews();
        news.setPublishDate(publishDate);
        return news;
    }

    public static News getNews(Instant publishDate, boolean publishEnabled) {
        News news = getNews();
        news.setPublishEnabled(publishEnabled);
        news.setPublishDate(publishDate);
        return news;
    }

    public static NewsCategory getNewsCategory() {
        return NewsCategory.builder()
                .id(Integer.valueOf(getNumeric(2)))
                .name(getAlphabeticString())
                .build();
    }

    public static NewsDto getNewsDto() {
        return NewsDto.builder()
                .id(Integer.parseInt(getNumeric(2)))
                .creatorId(Integer.parseInt(getNumeric(2)))
                .newsCategoryId(Integer.parseInt(getNumeric(2)))
                .createDate(Instant.now().toEpochMilli())
                .title(getAlphabeticString())
                .description(getAlphabeticString())
                .publishDate(Instant.now().toEpochMilli())
                .publishEnabled(true)
                .build();
    }

    public static NewsCategoryDto getNewsCategoryDto() {
        return NewsCategoryDto.builder()
                .id(Integer.valueOf(getNumeric(2)))
                .name(getAlphabeticString())
                .build();
    }

    public static Room getRoom() {
        return Room.builder()
                .id(Integer.valueOf(getNumeric(2)))
                .name(getAlphabeticString())
                .nurseStation(getNurseStation())
                .maxOccupancy(Integer.parseInt(getNumeric(2)))
                .comment(getAlphabeticString())
                .deleted(false)
                .build();
    }

    public static Block getBlock() {
        return Block.builder()
                .id(Integer.valueOf(getNumeric(2)))
                .name(getAlphabeticString())
                .comment(getAlphabeticString())
                .deleted(false)
                .build();
    }

    private static NurseStation getNurseStation() {
        return NurseStation.builder()
                .id(Integer.valueOf(getNumeric(2)))
                .name(getAlphabeticString())
                .comment(getAlphabeticString())
                .deleted(false)
                .build();
    }

}
