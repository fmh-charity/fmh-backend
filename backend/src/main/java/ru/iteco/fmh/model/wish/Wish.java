package ru.iteco.fmh.model.wish;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.user.Role;
import ru.iteco.fmh.model.user.User;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

/**
 * просьба
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "wish")
public class Wish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String title;
    String description;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    User creator;
    @ManyToOne
    @JoinColumn(name = "executor_id")
    User executor;

    Instant createDate;
    Instant planExecuteDate;
    Instant factExecuteDate;

    @Enumerated(EnumType.STRING)
    Status status;

    boolean deleted;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    Patient patient;

    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(name = "wish_visibility", joinColumns = @JoinColumn(name = "wish_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    List<Role> wishRoles;

    public void changeStatus(Status newStatus, User executor) {
        status.changeStatus(this, newStatus, executor);
    }
}
