package ru.iteco.fmh;

import ru.iteco.fmh.dto.admission.AdmissionDto;
import ru.iteco.fmh.dto.claim.ClaimCommentDto;
import ru.iteco.fmh.dto.claim.ClaimDto;
import ru.iteco.fmh.dto.news.NewsCategoryDto;
import ru.iteco.fmh.dto.news.NewsDto;
import ru.iteco.fmh.dto.patient.PatientDto;
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
import ru.iteco.fmh.model.task.StatusE;
import ru.iteco.fmh.model.task.claim.Claim;
import ru.iteco.fmh.model.task.claim.ClaimComment;
import ru.iteco.fmh.model.task.wish.Wish;
import ru.iteco.fmh.model.task.wish.WishComment;
import ru.iteco.fmh.model.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

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

    public static Wish getWish(StatusE statusE) {
        return Wish.builder()
                .id(Integer.valueOf(getNumeric(2)))
                .patient(getPatient())
                .creator(getUser())
                .executor(getUser())
                .description(getAlphabeticString())
                .createDate(LocalDateTime.now())
                .planExecuteDate(LocalDateTime.now())
                .factExecuteDate(LocalDateTime.now())
                .status(statusE)
                .build();
    }


    public static Patient getPatient() {
        Patient patient = Patient.builder()
                .id(Integer.valueOf(getNumeric(2)))
                .firstName(getAlphabeticString())
                .lastName(getAlphabeticString())
                .middleName(getAlphabeticString())
                .birthDate(LocalDate.now())
                .currentAdmission(Admission.builder().build())
                .build();
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
                .firstName(getAlphabeticString())
                .lastName(getAlphabeticString())
                .middleName(getAlphabeticString())
                .login(getAlphabeticString())
                .password(getAlphabeticString())
                .phoneNumber(getAlphabeticString())
                .build();
        String shortName = getShortName(userDto.getFirstName(), userDto.getLastName(), userDto.getMiddleName());
        userDto.setShortUserName(shortName);
        return userDto;

    }

    public static User getUser() {
        User user = User.builder()
                .id(Integer.valueOf(getNumeric(2)))
                .login(getAlphabeticString())
                .password(getAlphabeticString())
                .firstName(getAlphabeticString())
                .lastName(getAlphabeticString())
                .middleName(getAlphabeticString())
                .phoneNumber(getAlphabeticString())
                .email(getAlphabeticString())
                .build();
        String shortName = getShortName(user.getFirstName(), user.getLastName(), user.getMiddleName());
        user.setShortUserName(shortName);
        return user;

    }

    public static WishDto getWishDto(StatusE statusE) {
        WishDto wishDto = getWishDto();
        wishDto.setStatus(statusE);
        return wishDto;
    }

    public static WishDto getWishDto() {

        return WishDto.builder()
                .patient(getPatientDto())
                .description(getAlphabeticString())
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
                .firstName(getAlphabeticString())
                .lastName(getAlphabeticString())
                .middleName(getAlphabeticString())
                .birthDate(LocalDate.now())
                .build();
        return patientDto;
    }

    public static Admission getAdmission() {
        return getAdmission(ACTIVE);
    }

    public static Admission getAdmission(AdmissionsStatus status) {
        return Admission.builder()
                .id(Integer.valueOf(getNumeric(2)))
                .patient(getPatient())
                .planDateIn(LocalDateTime.now())
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
                .planDateIn(LocalDateTime.now())
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
                .createDate(LocalDateTime.now())
                .planExecuteDate(LocalDateTime.now())
                .factExecuteDate(null)
                .status(StatusE.IN_PROGRESS)
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
                .createDate(LocalDateTime.now())
                .planExecuteDate(LocalDateTime.now())
                .factExecuteDate(null)
                .status(StatusE.OPEN)
                .build();
    }

    public static ClaimDto getClaimDtoOpen() {

        return ClaimDto.builder()
                // TODO: убрать hardcode
                .title("Title")
                .description("description")
                .creatorId(3)
                .executorId(null)
                .planExecuteDate(LocalDateTime.now().withNano(0))
                .createDate(LocalDateTime.now().plusDays(2).withNano(0))
                .factExecuteDate(null)
                .status(StatusE.OPEN)
                .build();
    }

    public static ClaimDto getClaimDtoInProgress() {

        return ClaimDto.builder()
                // TODO: убрать hardcode
                .title("Title")
                .description("description")
                .creatorId(3)
                .executorId(3)
                .planExecuteDate(LocalDateTime.now().withNano(0))
                .createDate(LocalDateTime.now().plusDays(2).withNano(0))
                .factExecuteDate(null)
                .status(StatusE.IN_PROGRESS)
                .build();
    }


    public static ClaimCommentDto getClaimCommentDto() {
        return ClaimCommentDto.builder()
                .claimId(2)
                .creatorId(2)
                .description("description")
                .createDate(LocalDateTime.now())
                .build();
    }

    public static ClaimComment getClaimComment(Claim claim) {
        return ClaimComment.builder()
                .claim(claim)
                .creator(getUser())
                .description("description")
                .createDate(LocalDateTime.now())
                .build();
    }


    public static WishComment getWishComment(StatusE wishStatus) {
        return WishComment.builder()
                .id(Integer.valueOf(getNumeric(2)))
                .wish(getWish(wishStatus))
                .description(getAlphabeticString())
                .creator(getUser())
                .createDate(LocalDateTime.now())
                .build();
    }

    public static WishCommentDto getWishCommentDto(StatusE wishStatus) {
        return WishCommentDto.builder()
                .id(Integer.valueOf(getNumeric(2)))
                .wish(getWishDto(wishStatus))
                .description(getAlphabeticString())
                .creator(getUserDto())
                .createDate(LocalDateTime.now())
                .build();
    }


    public static News getNews() {
        return News.builder()
                .id(Integer.valueOf(getNumeric(2)))
                .creator(getUser())
                .newsCategory(getNewsCategory())
                .createDate(LocalDateTime.now())
                .title(getAlphabeticString())
                .description(getAlphabeticString())
                .publishDate(LocalDateTime.now())
                .publishEnabled(true)
                .build();
    }

    public static News getNews(LocalDateTime publishDate) {
        News news = getNews();
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
                .id(Integer.valueOf(getNumeric(2)))
                .creatorId(Integer.valueOf(getNumeric(2)))
                .newsCategoryId(Integer.valueOf(getNumeric(2)))
                .createDate(LocalDateTime.now())
                .title(getAlphabeticString())
                .description(getAlphabeticString())
                .publishDate(LocalDateTime.now())
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
                .block(getBlock())
                .nurseStation(getNurseStation())
                .maxOccupancy(Integer.valueOf(getNumeric(2)))
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
