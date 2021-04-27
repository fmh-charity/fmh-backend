package ru.iteco.fmh.model.user;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Пользователь
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@ToString
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;


    String login;
    String password;
    String firstName;
    String lastName;
    String middleName;
    String phoneNumber;
    String email;
    boolean deleted;

//    @OneToMany(mappedBy = "author")
//    List<Advertisement> advertisementCollections = new ArrayList<>();
//    @OneToMany(mappedBy = "creator")
//    List<Note> myNotes = new ArrayList<>();
//    @OneToMany(mappedBy = "executor")
//    List<Note> myTasks = new ArrayList<>();
//    @OneToMany(mappedBy = "creator")
//    List<Claim> myClaims = new ArrayList<>();
//    @OneToMany(mappedBy = "executor")
//    List<Claim> doClaims = new ArrayList<>();

}
