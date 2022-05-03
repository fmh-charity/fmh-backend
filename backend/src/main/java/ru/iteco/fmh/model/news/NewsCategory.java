package ru.iteco.fmh.model.news;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

/**
 * категория новости
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@ToString
@Table(name = "newsCategory")
public class NewsCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String name;
    boolean deleted;
}
