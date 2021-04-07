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
@Table(name = "nurseStation")
public class NurseStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "nurseStation")
    Collection<Room> roomsCollection = new ArrayList<>();

    String nurseStationName;
    String comment;


}
