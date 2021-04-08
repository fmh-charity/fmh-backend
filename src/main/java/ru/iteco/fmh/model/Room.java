package ru.iteco.fmh.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


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

    @ManyToOne
    Block block;
    @ManyToOne
    NurseStation nurseStation;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "room")
    List<Patient> patientCollection = new ArrayList<>();


    int maxCapacity;
    int currentOccupancy;
    String roomName;
    String comment;


}
