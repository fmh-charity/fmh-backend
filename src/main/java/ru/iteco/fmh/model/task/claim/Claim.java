package ru.iteco.fmh.model.task.claim;

import lombok.ToString;
import lombok.experimental.SuperBuilder;
import ru.iteco.fmh.model.task.Status;
import ru.iteco.fmh.model.task.Task;
import ru.iteco.fmh.model.user.User;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.Instant;

/**
 * Заявка
 */
@SuperBuilder
@ToString(callSuper = true)
@Entity
@Table(name = "claim")
public class Claim extends Task {
    public Claim(Integer id, String title, String description, User creator,
                 User executor, Instant createDate, Instant planExecuteDate,
                 Instant factExecuteDate, Status status, boolean deleted) {
        super(id, title, description, creator, executor, createDate, planExecuteDate,
                factExecuteDate, status, deleted);
    }

    public Claim() {
        super();
    }
}
