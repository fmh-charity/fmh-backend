package ru.iteco.fmh.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
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
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @OneToMany(mappedBy = "author")
    List<Advertisement> advertisementCollections = new ArrayList<>();
    @OneToMany(mappedBy = "creator")
    List<Note> myNotes = new ArrayList<>();
    @OneToMany(mappedBy = "executor")
    List<Note> myTasks = new ArrayList<>();
    @OneToMany(mappedBy = "creator")
    List<Claim> myClaims = new ArrayList<>();
    @OneToMany(mappedBy = "executor")
    List<Claim> doClaims = new ArrayList<>();

    String login;
    String password;
    String firstName;
    String middleName;
    String lastName;
    String phoneNumber;
    String email;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
