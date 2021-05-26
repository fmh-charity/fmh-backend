package ru.iteco.fmh.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Formula;
import ru.iteco.fmh.model.admission.Admission;
import ru.iteco.fmh.model.admission.AdmissionsStatus;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Пациент
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@ToString
@Table(name = "patient")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String firstName;
    String lastName;
    String middleName;
    LocalDate birthDate;

    @OneToOne
    @JoinColumn(name = "current_admission_id")
    Admission currentAdmission;

    boolean deleted;

    @Formula("CONCAT_WS(' ', last_name ," + "CONCAT_WS('.', SUBSTRING(UPPER(first_name),1,1), " + "SUBSTRING(UPPER(middle_name),1,1) )" + ")")
    String shortPatientName;

    public AdmissionsStatus getStatus() {
        return currentAdmission != null ? currentAdmission.getStatus() : AdmissionsStatus.EXPECTED;
    }
}
