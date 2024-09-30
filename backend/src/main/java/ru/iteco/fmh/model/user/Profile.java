package ru.iteco.fmh.model.user;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "profile")
@Where(clause = "deleted=false")
@SQLDelete(sql = "UPDATE profile SET deleted = true WHERE id=?")
public class Profile {
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

    @OneToOne(mappedBy = "profile", cascade = CascadeType.ALL)
    User user;
    String email;
    LocalDate dateOfBirth;
    boolean deleted;
    boolean emailConfirmed;

    public Profile(String test, String test1, String test2, User user, String alphabeticString, LocalDate now, boolean b, boolean b1) {
        this.firstName = test;
        this.lastName = test1;
        this.middleName = test2;
        this.user = user;
        this.email = alphabeticString;
        this.dateOfBirth = now;
        this.deleted = b;
        this.emailConfirmed = b1;
    }
}
