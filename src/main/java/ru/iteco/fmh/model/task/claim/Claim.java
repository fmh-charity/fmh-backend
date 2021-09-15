package ru.iteco.fmh.model.task.claim;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ru.iteco.fmh.model.task.StatusE;
import ru.iteco.fmh.model.task.Task;
import ru.iteco.fmh.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Заявка
 */
@SuperBuilder
@ToString(callSuper = true)
@Entity
@Table(name = "claim")
public class Claim extends Task {
    public Claim(Integer id, String title, String description, User creator,
                 User executor, LocalDateTime createDate, LocalDateTime planExecuteDate,
                 LocalDateTime factExecuteDate, StatusE status, boolean deleted) {
        super(id, title, description, creator, executor, createDate, planExecuteDate,
                factExecuteDate, status, deleted);
    }

    public Claim() {
        super();
    }
}
