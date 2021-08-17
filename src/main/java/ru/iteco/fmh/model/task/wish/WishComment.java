package ru.iteco.fmh.model.task.wish;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.iteco.fmh.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@ToString
@Table(name = "wishComment")
public class WishComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @ManyToOne
    @JoinColumn(name = " wish_id")
    Wish wish;

    String description;
    @ManyToOne
    @JoinColumn(name = "creator_id")
    User creator;
    LocalDateTime createDate;


}
