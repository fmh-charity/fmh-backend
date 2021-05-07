package ru.iteco.fmh.testentity;

import ru.iteco.fmh.dto.patient.PatientAdmissionDto;

import java.util.ArrayList;
import java.util.List;

public class PatientAdmissionDtoList {
    private List<PatientAdmissionDto> patientAdmissionDtoList;

    public PatientAdmissionDtoList() {
        this.patientAdmissionDtoList = new ArrayList<>();
    }

    public List<PatientAdmissionDto> getPatientAdmissionDtoList() {
        return patientAdmissionDtoList;
    }

    public void setPatientAdmissionDtoList(List<PatientAdmissionDto> patientAdmissionDtoList) {
        this.patientAdmissionDtoList = patientAdmissionDtoList;
    }
}
