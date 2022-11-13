package ru.iteco.fmh.model.admission;

/**
 * Enum для статуса госпитализации
 */

public enum AdmissionsStatus {
    //Выписан
    DISCHARGED(0),
    //В хосписе
    ACTIVE(2),
    //Ожидается
    EXPECTED(1);

    AdmissionsStatus(int weight) {
    }
}
