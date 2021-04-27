package ru.iteco.fmh.model.admission;

/**
 * Enum для статуса госпитализации
 */

public enum AdmissionsStatusE {
    //Выписан
    Discharged,
    //В хосписе
    Active,
    //Ожидается
    Expected;

    AdmissionsStatusE() {
    }
}
