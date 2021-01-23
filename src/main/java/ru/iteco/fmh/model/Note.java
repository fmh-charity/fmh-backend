package ru.iteco.fmh.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "note")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    Patient patient;
    @ManyToOne
    Employee author;
    @ManyToOne
    Employee doer;

    String text;
    LocalDate dateCreate;
    LocalDate dateUpdate;
    LocalDate dateEnd;
    String comment;

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", patient=" + patient.getId() +
                ", text='" + text + '\'' +
                ", author_id=" + author +
                ", doer_id=" + doer +
                ", dateCreate=" + dateCreate +
                ", dateUpdate=" + dateUpdate +
                ", dateEnd=" + dateEnd +
                ", comment='" + comment + '\'' +
                '}';
    }
}
