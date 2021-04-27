package ru.iteco.fmh.model.admission;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

/**
 * Статус госпитализации
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@ToString
@Table(name = "adm_status")
public class AdmissionsStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    AdmissionsStatusE name;
    String code;
    boolean deleted;
}
