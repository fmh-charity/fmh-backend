package ru.iteco.fmh.model.task;

import ru.iteco.fmh.model.user.User;

import java.time.Instant;

/**
 * Enum для статуса заявки и записки
 */
public enum StatusE {
    IN_PROGRESS("В РАБОТЕ") {
        @Override
        public void changeStatus(Task task, StatusE newStatus, User executor) {
            if (CANCELLED == newStatus) {
                throw new IllegalArgumentException("нельзя перевести из статуса "
                        + this.getName()
                        + " в статус "
                        + newStatus.getName());
            }
            if (EXECUTED == newStatus) {
                task.setFactExecuteDate(Instant.now());
            }
            if (OPEN == newStatus) {
                task.setExecutor(null);
            }
            task.setStatus(newStatus);
        }
    },

    CANCELLED("ОТМЕНЕНО") {
        @Override
        public void changeStatus(Task task, StatusE newStatus, User executor) {
            throw new IllegalArgumentException("нельзя перевести из статуса " + this.getName() + " в иной статус");
        }
    },

    OPEN("ОТКРЫТО") {
        @Override
        public void changeStatus(Task task, StatusE newStatus, User executor) {
            if (EXECUTED == newStatus) {
                throw new IllegalArgumentException("нельзя перевести из статуса "
                        + this.getName()
                        + " в статус "
                        + newStatus.getName());
            }
            if (IN_PROGRESS == newStatus) {
                task.setExecutor(executor);
            }

            task.setStatus(newStatus);
        }
    },

    EXECUTED("ИСПОЛНЕНО") {
        @Override
        public void changeStatus(Task task, StatusE newStatus, User executor) {
            throw new IllegalArgumentException("нельзя перевести из статуса " + this.getName() + " в иной статус");
        }
    };

    private final String name;

    StatusE(String name) {
        this.name = name;
    }

    public abstract void changeStatus(Task task, StatusE newStatus, User executor);

    public String getName() {
        return name;
    }
}
