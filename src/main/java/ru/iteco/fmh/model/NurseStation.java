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
@ToString
public class NurseStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;


    String nurseStationName;
    String comment;


}
