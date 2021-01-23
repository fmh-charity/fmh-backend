package ru.iteco.fmh.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @OneToMany(mappedBy = "author")
    Collection<Advertisement> advertisementCollections = new ArrayList<>();
    @OneToMany(mappedBy = "author")
    Collection<Note> myNotes = new ArrayList<>();
    @OneToMany(mappedBy = "doer")
    Collection<Note> myTasks = new ArrayList<>();
    @OneToMany(mappedBy = "author")
    Collection<Claim> myClaims = new ArrayList<>();
    @OneToMany(mappedBy = "doer")
    Collection<Claim> doClaims = new ArrayList<>();

    String firstName;
    String middleName;
    String lastName;

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
