package ru.iteco.fmh.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    String roomName;

    //many room can have one block
    @ManyToOne
    Block block;
    //many room can have one nurseStation
    @ManyToOne
    NurseStation nurseStation;

    int maxCapacity;

    String comment;
    boolean deleted;

//    @ToString.Exclude
//    @OneToMany(fetch = FetchType.EAGER, mappedBy = "room")
//    List<Patient> patientCollection = new ArrayList<>();
//    int currentOccupancy;

}
