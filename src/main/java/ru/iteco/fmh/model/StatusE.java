package ru.iteco.fmh.model;

/**
 * Enum для статуса заявки и записки
 */
public enum StatusE {
    //активна
    active,
    // отменена
    canceled,
    //исполнена
    executed;

    StatusE() {
    }
}
