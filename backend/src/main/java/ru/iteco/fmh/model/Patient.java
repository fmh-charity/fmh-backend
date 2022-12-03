package ru.iteco.fmh.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Where;
import ru.iteco.fmh.model.admission.Admission;
import ru.iteco.fmh.model.admission.AdmissionsStatus;

import javax.persistence.*;
import java.time.Instant;
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
    Instant createDate;
    String firstName;
    String lastName;
    String middleName;
    Instant birthDate;

    @Where(clause = "deleted = false")
    @OneToOne
    @JoinColumn(name = "current_admission_id")
    Admission currentAdmission;

    @Transient
    AdmissionsStatus status = getStatus();

    @Where(clause = "deleted = false")
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    @ToString.Exclude
    Set<Admission> admissions = new HashSet<>();

    boolean deleted;

    public AdmissionsStatus getStatus() {
        return currentAdmission != null ? currentAdmission.getStatus() : AdmissionsStatus.EXPECTED;
    }

}
