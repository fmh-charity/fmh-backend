package ru.iteco.fmh.model.employee;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "positions")
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "position_name")
    String name;
}
