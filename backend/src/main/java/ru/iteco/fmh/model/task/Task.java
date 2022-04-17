package ru.iteco.fmh.model.task;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.iteco.fmh.model.user.User;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@MappedSuperclass
public class Task {
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

    public void changeStatus(Status newStatus, User executor) {
        status.changeStatus(this, newStatus, executor);
    }
}
