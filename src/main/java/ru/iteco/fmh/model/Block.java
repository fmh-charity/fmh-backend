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
@Table(name = "block")
@ToString
public class Block {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;


    byte[] symbol;
    String blocksName;
    String comment;


}
