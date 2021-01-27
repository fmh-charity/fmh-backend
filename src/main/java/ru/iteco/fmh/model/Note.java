package ru.iteco.fmh.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    User creator;
    @ManyToOne
    User executor;

//    Status status;

    String description;
    LocalDateTime createDate;
    LocalDateTime planExecuteTime;
    LocalDateTime factExecuteTime;
    String comment;

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", patient=" + patient +
                ", creator=" + creator +
                ", executor=" + executor +
                ", description='" + description + '\'' +
                ", createDate=" + createDate +
                ", planExecuteDate=" + planExecuteTime +
                ", factExecuteDate=" + factExecuteTime +
                ", comment='" + comment + '\'' +
                '}';
    }
}
