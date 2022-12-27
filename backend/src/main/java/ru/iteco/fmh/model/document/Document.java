package ru.iteco.fmh.model.document;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import ru.iteco.fmh.model.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
@Where(clause = "deleted=false")
@SQLDelete(sql = "UPDATE document SET deleted = true WHERE id=?")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @NotNull
    @Column(unique = true)
    String name;

    String description;
    boolean deleted;

    @NotNull
    @Enumerated(EnumType.STRING)
    DocumentStatus status = DocumentStatus.NEW;

    Instant createDate;

    @NotNull
    String filePath;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
