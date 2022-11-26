package ru.iteco.fmh.model.task.wish;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.iteco.fmh.model.Patient;
import ru.iteco.fmh.model.task.Status;
import ru.iteco.fmh.model.task.Task;
import ru.iteco.fmh.model.user.Role;
import ru.iteco.fmh.model.user.User;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.Set;

/**
 * просьба
 */

@SuperBuilder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "wish")
public class Wish extends Task {
    @ManyToOne
    @JoinColumn(name = "patient_id")
    Patient patient;

    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
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

    @Override
    public String toString() {
        return "Wish{" +
                "patient=" + patient +
                ", wishVisibility=" + wishVisibility +
                '}';
    }
}
