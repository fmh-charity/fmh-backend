package ru.iteco.fmh.model.task;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import ru.iteco.fmh.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    LocalDateTime createDate;
    LocalDateTime planExecuteDate;
    LocalDateTime factExecuteDate;

    @Enumerated(EnumType.STRING)
    StatusE status;

    boolean deleted;

    public void changeStatus(StatusE newStatus) {
        status.changeStatus(this, newStatus);
    }
}
