package ru.iteco.fmh.model.task;

import java.time.LocalDateTime;

/**
 * Enum для статуса заявки и записки
 */
public enum StatusE {
    IN_PROGRESS("В РАБОТЕ") {
        @Override
        public void changeStatus(Task task, StatusE newStatus) {
            if (CANCELLED==newStatus) throw new IllegalArgumentException("нельзя перевести из статуса " + IN_PROGRESS.getName()
                    +  "в статус " + CANCELLED.getName());
            if (EXECUTED==newStatus) task.setFactExecuteDate(LocalDateTime.now().withNano(0));
            task.setStatus(newStatus);
        }
    },

    CANCELLED("ОТМЕНЕНО") {
        @Override
        public void changeStatus(Task task, StatusE newStatus) {
            throw new IllegalArgumentException("нельзя перевести из статуса " + CANCELLED.getName() +  " в иной статус");
        }
    },

    OPEN("ОТКРЫТО") {
        @Override
        public void changeStatus(Task task, StatusE newStatus) {
            if (EXECUTED == newStatus) throw new IllegalArgumentException("нельзя перевести из статуса " + OPEN.getName()
                    +  "в статус " + EXECUTED.getName());
            task.setStatus(newStatus);
        }
    },

    EXECUTED("ИСПОЛНЕНО") {
        @Override
        public void changeStatus(Task task, StatusE newStatus) {
            throw new IllegalArgumentException("нельзя перевести из статуса " + EXECUTED.getName() +  " в иной статус");
        }
    };

    private final String name;

    StatusE(String name) {
        this.name = name;
    }

    public abstract void changeStatus(Task task, StatusE newStatus);

    public String getName() {
        return name;
    }
}
