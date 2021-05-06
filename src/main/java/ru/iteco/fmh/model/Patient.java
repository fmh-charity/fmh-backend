package ru.iteco.fmh.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.iteco.fmh.model.admission.Admission;
import ru.iteco.fmh.model.admission.AdmissionsStatus;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Optional;

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
    LocalDate birthday;

    @OneToOne//но я не уверена
    Admission currentAdmission;

    String shortPatientName;

    boolean deleted;

    public AdmissionsStatus getStatus() {
        return currentAdmission != null ? currentAdmission.getAdmissionsStatus() : AdmissionsStatus.EXPECTED;
    }

//    TODO: дорабоать
//    @OneToMany(fetch = FetchType.EAGER, mappedBy = "patient")
//    List<Note> noteCollection = new ArrayList<>();

//    @Formula(
//            "CONCAT_WS(' ', first_name ," +
//                    "CONCAT_WS('.', SUBSTRING(UPPER(middle_name),1,1), " +
//                    "SUBSTRING(UPPER(last_name),1,1) )" +
//                    ")")


}
