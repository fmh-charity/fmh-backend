package ru.iteco.fmh.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import ru.iteco.fmh.model.user.Profile;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Пациент
 */

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "patient")
@Where(clause = "deleted=false")
@SQLDelete(sql = "UPDATE patient SET deleted = true WHERE id=?")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    boolean deleted;

    LocalDate planDateIn;
    LocalDate planDateOut;
    LocalDate factDateIn;
    LocalDate factDateOut;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    PatientStatus status = PatientStatus.EXPECTED;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    Profile profile;

    @JoinColumn(name = "room_id")
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    Room room;
}
