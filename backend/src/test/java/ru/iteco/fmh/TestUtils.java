package ru.iteco.fmh;

import ru.iteco.fmh.dto.admission.AdmissionDto;
import ru.iteco.fmh.dto.document.DocumentCreationDtoRq;
import ru.iteco.fmh.dto.news.NewsCategoryDto;
import ru.iteco.fmh.dto.news.NewsDto;
import ru.iteco.fmh.dto.patient.PatientCreateInfoDtoRq;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.dto.patient.PatientDtoIdFio;
import ru.iteco.fmh.dto.user.ProfileDtoRs;
import ru.iteco.fmh.dto.user.UserDto;
import ru.iteco.fmh.dto.user.UserDtoIdFio;
import ru.iteco.fmh.dto.wish.WishCommentDto;
import ru.iteco.fmh.dto.wish.WishCreationRequest;
import ru.iteco.fmh.dto.wish.WishDto;
import ru.iteco.fmh.model.Block;
import ru.iteco.fmh.model.NurseStation;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.PatientStatus;
import ru.iteco.fmh.model.Room;
import ru.iteco.fmh.model.news.News;
import ru.iteco.fmh.model.news.NewsCategory;
import ru.iteco.fmh.model.user.Profile;
import ru.iteco.fmh.model.wish.Status;
import ru.iteco.fmh.model.wish.Wish;
import ru.iteco.fmh.model.wish.WishComment;
import ru.iteco.fmh.model.user.Role;
import ru.iteco.fmh.model.user.User;
import ru.iteco.fmh.dto.registration.RegistrationRequest;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static ru.iteco.fmh.model.PatientStatus.ACTIVE;


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
                .creator(getUser(getProfile()))
                .description(getAlphabeticString())
                .createDate(Instant.now())
                .planExecuteDate(Instant.now())
                .factExecuteDate(Instant.now())
                .status(status)
                .wishRoles(List.of(getRole("ROLE_ADMINISTRATOR")))
                .executors(Collections.emptySet())
                .build();
    }

    public static WishCreationRequest getWishCreationInfoDto(Status status) {
        return WishCreationRequest.builder()
                .patientId(2)
                .title(getAlphabeticString())
                .description(getAlphabeticString())
                .planExecuteDate(Instant.now())
                .wishVisibility(List.of(1))
                .build();
    }

    public static Patient getPatient() {
        Patient patient = Patient.builder()
                .id(Integer.valueOf(getNumeric(2)))
                .firstName(getAlphabeticString())
                .lastName(getAlphabeticString())
                .middleName(getAlphabeticString())
                .birthDate(LocalDate.now())
                .planDateIn(LocalDate.now())
                .factDateIn(LocalDate.now())
                .planDateOut(LocalDate.now())
                .factDateOut(LocalDate.now())
                .room(null)
                .build();
        return patient;
    }

    public static PatientCreateInfoDtoRq getPatientCreateInfoDtoRq() {
        PatientCreateInfoDtoRq patient = PatientCreateInfoDtoRq.builder()
                .firstName(getAlphabeticString())
                .lastName(getAlphabeticString())
                .middleName(getAlphabeticString())
                .birthDate(LocalDate.now())
                .dateIn(LocalDate.now())
                .dateInBoolean(false)
                .status(ACTIVE)
                .build();
        return patient;
    }

    public static UserDto getUserDto(ProfileDtoRs profileDtoRs) {
        return UserDto.builder()
                .id(Integer.valueOf(getNumeric(2)))
                .login(getAlphabeticString())
                .firstName(profileDtoRs.getFirstName())
                .lastName(profileDtoRs.getLastName())
                .middleName(profileDtoRs.getMiddleName())
                .email(profileDtoRs.getEmail())
                .build();
    }

    public static ProfileDtoRs getProfileDtoRs() {
        return ProfileDtoRs.builder()
                .firstName(getAlphabeticString())
                .lastName(getAlphabeticString())
                .middleName(getAlphabeticString())
                .email(getAlphabeticString())
                .dateOfBirth(LocalDate.now())
                .build();
    }

    public static User getUser(Set<Role> roles, Profile profile) {
        return User.builder()
                .id(Integer.valueOf(getNumeric(2)))
                .login(getAlphabeticString())
                .password(getAlphabeticString())
                .userRoles(roles)
                .profile(profile)
                .build();
    }

    public static User getUser(Profile profile) {
        return User.builder()
                .id(Integer.valueOf(getNumeric(2)))
                .login(getAlphabeticString())
                .password(getAlphabeticString())
                .profile(profile)
                .build();
    }

    public static Profile getProfile() {
        return Profile.builder()
                .id(Integer.valueOf(getNumeric(2)))
                .firstName(getAlphabeticString())
                .lastName(getAlphabeticString())
                .middleName(getAlphabeticString())
                .email(getAlphabeticString())
                .dateOfBirth(LocalDate.now())
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
                .executor(new UserDtoIdFio(444, "ExecutorTestName",
                        "ExecutorTestMiddleName", "ExecutorTestLastName"))
                .creator(new UserDtoIdFio(555, "CreatorTestName",
                        "CreatorTestMiddleName", "CreatorTestLastName"))
                .status(Status.OPEN)
                .build();
    }

    public static WishCreationRequest getWishCreationInfoDto() {

        return WishCreationRequest.builder()
                .patientId(2)
                .title(getAlphabeticString())
                .description(getAlphabeticString())
                .planExecuteDate(Instant.now())
                .wishVisibility(List.of(1))
                .build();
    }

    public static PatientDto getPatientDto() {
        PatientDto patientDto = PatientDto.builder()
                .firstName(getAlphabeticString())
                .lastName(getAlphabeticString())
                .middleName(getAlphabeticString())
                .birthDate(LocalDate.now())
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

    public static AdmissionDto getAdmissionDto() {
        return getAdmissionDto(ACTIVE);
    }

    public static AdmissionDto getAdmissionDto(PatientStatus status) {
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

    public static WishComment getWishComment(Status wishStatus) {
        return WishComment.builder()
                .id(Integer.valueOf(getNumeric(2)))
                .wish(getWish(wishStatus))
                .description(getAlphabeticString())
                .creator(getUser(getProfile()))
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
                .creator(getUser(getProfile()))
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
                .newsCategoryId(Integer.parseInt(getNumeric(2)))
                .title(getAlphabeticString())
                .description(getAlphabeticString())
                .publishDate(Instant.now().toEpochMilli())
                .publishEnabled(true)
                .build();
    }

    public static NewsDto getNewsDtowithDateAndUser() {
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

    private static NurseStation getNurseStation() {
        return NurseStation.builder()
                .id(Integer.valueOf(getNumeric(2)))
                .name(getAlphabeticString())
                .comment(getAlphabeticString())
                .deleted(false)
                .build();
    }

    public static DocumentCreationDtoRq getDocumentCreationDtoRq() {
        DocumentCreationDtoRq doc = DocumentCreationDtoRq.builder()
                .name(getAlphabeticString())
                .description(getAlphabeticString())
                .filePath(getAlphabeticString())
                .build();
        return doc;
    }

    public static RegistrationRequest getRegistrationRequest() {
        return RegistrationRequest.builder()
                .firstName(getAlphabeticString())
                .middleName(getAlphabeticString())
                .lastName(getAlphabeticString())
                .dateOfBirth(LocalDate.now())
                .roleIds(List.of(2))
                .email(getAlphabeticString())
                .password(getAlphabeticString())
                .build();
    }
}
