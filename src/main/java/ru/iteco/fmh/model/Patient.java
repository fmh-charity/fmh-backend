package ru.iteco.fmh.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "patient")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "patient")
    List<Note> noteCollection = new ArrayList<>();

    @ManyToOne
    Room room;

    String firstName;
    String middleName;
    String lastName;
    LocalDate birthday;
    PatientsStatus patientsStatus;
//    @Formula(
//            "CONCAT_WS(' ', first_name ," +
//                    "CONCAT_WS('.', SUBSTRING(UPPER(middle_name),1,1), " +
//                    "SUBSTRING(UPPER(last_name),1,1) )" +
//                    ")")
//    String shortPatientName;


    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", room=" + room +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthday=" + birthday +
                ", patientsStatus=" + patientsStatus +
                '}';
    }
}
