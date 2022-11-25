package ru.iteco.fmh.model.task.wish;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.task.Status;
import ru.iteco.fmh.model.task.Task;
import ru.iteco.fmh.model.user.Role;
import ru.iteco.fmh.model.user.User;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

/**
 * просьба
 */

@SuperBuilder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@Entity
@Table(name = "wish")
public class Wish extends Task {
    @ManyToOne
    @JoinColumn(name = "patient_id")
    Patient patient;

    @ManyToMany
    @JoinTable(name = "wish_visibility", joinColumns = @JoinColumn(name = "wish_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    List<Role> wishVisibility;

    public Wish() {
        super();
    }

    public Wish(Integer id, String title, String description, User creator, User executor, Instant createDate,
                Instant planExecuteDate, Instant factExecuteDate, Status status,
                boolean deleted, Patient patient, List<Role> wishVisibility) {
        super(id, title, description, creator, executor, createDate,
                planExecuteDate, factExecuteDate, status, deleted);
        this.patient = patient;
        this.wishVisibility = wishVisibility;
    }
}
