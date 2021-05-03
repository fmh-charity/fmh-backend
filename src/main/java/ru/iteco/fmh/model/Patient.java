package ru.iteco.fmh.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import ru.iteco.fmh.model.admission.Admission;
import ru.iteco.fmh.model.admission.AdmissionsStatusE;
import ru.iteco.fmh.service.admission.AdmissionService;

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

    boolean deleted;

    // возвращаем статус пациента текущей госпитализации
    // если госпитализации нет - то статус пациента AdmissionsStatusE.Expected
    public AdmissionsStatusE getStatus() {
        return Optional.ofNullable(currentAdmission.getAdmissionsStatus()).orElse(AdmissionsStatusE.Expected);
    }

//    @OneToMany(fetch = FetchType.EAGER, mappedBy = "patient")
//    List<Note> noteCollection = new ArrayList<>();

//    @Formula(
//            "CONCAT_WS(' ', first_name ," +
//                    "CONCAT_WS('.', SUBSTRING(UPPER(middle_name),1,1), " +
//                    "SUBSTRING(UPPER(last_name),1,1) )" +
//                    ")")
//    String shortPatientName;

}
