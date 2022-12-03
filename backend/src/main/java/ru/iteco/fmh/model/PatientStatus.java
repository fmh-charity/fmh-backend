package ru.iteco.fmh.model;

import lombok.NoArgsConstructor;

/**
 * Enum для статуса госпитализации
 */

@NoArgsConstructor
public enum PatientStatus {
    //Выписан
    DISCHARGED,
    //В хосписе
    ACTIVE,
    //Ожидается
    EXPECTED
}
