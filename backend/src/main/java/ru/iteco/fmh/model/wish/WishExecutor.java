package ru.iteco.fmh.model.wish;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.iteco.fmh.model.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@ToString
@Table(name = "wish_executors")
public class WishExecutor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "wish_id")
    Wish wish;

    @ManyToOne
    @JoinColumn(name = "executor_id")
    User executor;

    @NotNull
    Instant joinDate;

    Instant finishDate;
}
