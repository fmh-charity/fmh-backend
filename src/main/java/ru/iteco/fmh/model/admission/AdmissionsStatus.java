package ru.iteco.fmh.model.admission;

/**
 * Enum для статуса госпитализации
 */

public enum AdmissionsStatus {
    //Выписан
    DISCHARGED,
    //В хосписе
    ACTIVE,
    //Ожидается
    EXPECTED;

    AdmissionsStatus() {
    }
}
