package ru.iteco.fmh.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    Block block;
    @ManyToOne
    NurseStation nurseStation;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "room")
    Collection<Patient> patientCollection = new ArrayList<>();


    int maxCapacity;
    int currentOccupancy;
    String roomName;
    String comment;


}
