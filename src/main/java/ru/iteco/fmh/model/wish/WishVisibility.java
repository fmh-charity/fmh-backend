package ru.iteco.fmh.model.wish;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.iteco.fmh.model.user.Role;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@ToString
@Table(name = "wishVisibility")
public class WishVisibility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = " wish_id")
    Wish wish;
    @ManyToOne
    @JoinColumn(name = "role_id")
    Role role;
    Boolean deleted;
}
