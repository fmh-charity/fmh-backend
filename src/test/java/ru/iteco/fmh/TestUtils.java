package ru.iteco.fmh;

import ru.iteco.fmh.dto.note.NoteDto;
import ru.iteco.fmh.dto.patient.PatientDto;
import ru.iteco.fmh.model.Note;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.admission.Admission;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

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

    public static Note getNote() {
        Note note = Note.builder()
                .id(Integer.valueOf(getNumeric(2)))
                .patient(getPatient())
                .creator(null)
                .executor(null)
                .description(getAlphabeticString())
                .createDate(LocalDateTime.now())
                .planExecuteTime(LocalDateTime.now())
                .factExecuteTime(LocalDateTime.now())
                .comment(getAlphabeticString())
                .build();
        return note;
    }

    public static Patient getPatient() {
        Patient patient = Patient.builder()
                .id(Integer.valueOf(getNumeric(2)))
                .firstName(getAlphabeticString())
                .lastName(getAlphabeticString())
                .middleName(getAlphabeticString())
                .birthDate(LocalDate.now())
                .currentAdmission(Admission.builder().build())
                .shortPatientName(getAlphabeticString())
                .build();
        return patient;
    }

    public static NoteDto getNoteDto() {
        NoteDto noteDto = NoteDto.builder()
                .patient(new PatientDto())
                .description(getAlphabeticString())
                .planExecuteTime(LocalDateTime.now())
                .executor(null)
                .comment(getAlphabeticString())
                .build();
        return noteDto;
    }

    public static PatientDto getPatientDto() {
        PatientDto patientDto = PatientDto.builder()
                .id(Integer.valueOf(getNumeric(2)))
                .firstName(getAlphabeticString())
                .lastName(getAlphabeticString())
                .middleName(getAlphabeticString())
                .birthDate(LocalDate.now())
                .shortPatientName(getAlphabeticString())
                .build();
        return patientDto;
    }
}
