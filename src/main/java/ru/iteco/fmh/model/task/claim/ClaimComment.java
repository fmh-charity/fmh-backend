package ru.iteco.fmh.model.task.claim;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.iteco.fmh.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "claimComment")
public class ClaimComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @ManyToOne
    @JoinColumn(name = "claim_id")
    Claim claim;

    String description;
    @ManyToOne
    @JoinColumn(name = "creator_id")
    User creator;
    LocalDateTime createDate;
}
