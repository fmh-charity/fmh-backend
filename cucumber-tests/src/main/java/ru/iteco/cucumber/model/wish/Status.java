package ru.iteco.cucumber.model.wish;

import java.time.Instant;

/**
 * Enum для статуса заявки и записки
 */
public enum Status {
    IN_PROGRESS,

    CANCELLED,

    OPEN,

    EXECUTED,

    READY,

    READY_CHECK
}
