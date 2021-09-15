package ru.iteco.fmh.model.task;

import java.time.LocalDateTime;

/**
 * Enum для статуса заявки и записки
 */
public enum StatusE {
    IN_PROGRESS("В РАБОТЕ") {
        @Override
        public void changeStatus(Task task, StatusE newStatus) {
            if (CANCELLED == newStatus) {
                throw new IllegalArgumentException("нельзя перевести из статуса "
                        + this.getName()
                        + " в статус "
                        + newStatus.getName());
            }
            if (EXECUTED == newStatus) {
                task.setFactExecuteDate(LocalDateTime.now().withNano(0));
            }
            if (OPEN == newStatus) {
                task.setExecutor(null);
            }
            task.setStatus(newStatus);
        }
    },

    CANCELLED("ОТМЕНЕНО") {
        @Override
        public void changeStatus(Task task, StatusE newStatus) {
            throw new IllegalArgumentException("нельзя перевести из статуса " + this.getName() + " в иной статус");
        }
    },

    OPEN("ОТКРЫТО") {
        @Override
        public void changeStatus(Task task, StatusE newStatus) {
            if (EXECUTED == newStatus) {
                throw new IllegalArgumentException("нельзя перевести из статуса "
                        + this.getName()
                        + " в статус "
                        + newStatus.getName());
            }
            if (IN_PROGRESS == newStatus) {
                //  TODO: task.setExecutor(); - взять значение user'а из spring security context
            }
            task.setStatus(newStatus);
        }
    },

    EXECUTED("ИСПОЛНЕНО") {
        @Override
        public void changeStatus(Task task, StatusE newStatus) {
            throw new IllegalArgumentException("нельзя перевести из статуса " + this.getName() + " в иной статус");
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
