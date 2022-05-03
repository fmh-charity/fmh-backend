package ru.iteco.fmh.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

/**
 * Палата
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "room")
@ToString
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String name;

    @ManyToOne
    @JoinColumn(name = "block_id")
    Block block;

    @ManyToOne
    @JoinColumn(name = "nurse_station_id")
    NurseStation nurseStation;

    int maxOccupancy;

    String comment;
    boolean deleted;
}
