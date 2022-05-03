package ru.iteco.fmh.model.news;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.iteco.fmh.model.user.User;

import javax.persistence.*;
import java.time.Instant;

/**
 * Новость
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@ToString
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "news_category_id")
    NewsCategory newsCategory;

    String title;
    String description;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    User creator;

    Instant createDate;
    Instant publishDate;
    boolean publishEnabled;
    boolean deleted;
}
