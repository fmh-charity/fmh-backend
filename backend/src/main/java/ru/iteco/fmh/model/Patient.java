package ru.iteco.fmh.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Where;
import ru.iteco.fmh.model.admission.Admission;
import ru.iteco.fmh.model.admission.AdmissionsStatus;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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

    @NotBlank()
    @Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z]+$")
    String firstName;

    @NotBlank()
    @Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z]+(-[а-яА-ЯёЁa-zA-Z]+)?$")
    String lastName;

    @Pattern(regexp = "^[а-яА-ЯёЁa-zA-Z-]+$")
    String middleName;

    LocalDate birthDate;

    @Where(clause = "deleted = false")
    @OneToOne
    @JoinColumn(name = "current_admission_id")
    Admission currentAdmission;

    @Where(clause = "deleted = false")
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    Set<Admission> admissions = new HashSet<>();

    boolean deleted;

    public AdmissionsStatus getStatus() {
        return currentAdmission != null ? currentAdmission.getStatus() : AdmissionsStatus.EXPECTED;
    }
}
