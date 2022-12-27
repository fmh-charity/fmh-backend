package ru.iteco.fmh.model.document;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.iteco.fmh.model.user.User;

import javax.persistence.*;
import java.time.Instant;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@ToString
@Table(name = "document")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(unique = true)
    String name;

    String description;
    boolean deleted;

    @Enumerated(EnumType.STRING)
    DocumentStatus status;

    Instant createDate;
    String filePath;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
