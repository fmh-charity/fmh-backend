package ru.iteco.fmh.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

/**
 * Палата
 */
@Builder(toBuilder = true)
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

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "block_id")
    Block block;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "nurse_station_id")
    NurseStation nurseStation;

    int maxOccupancy;

    String comment;
    boolean deleted;
}
