package ru.iteco.fmh.model.wish;

import ru.iteco.fmh.model.user.User;

import java.time.Instant;

/**
 * Enum для статуса заявки и записки
 */
public enum Status {
    IN_PROGRESS("В РАБОТЕ") {
        @Override
        public void changeStatus(Wish wish, Status newStatus, User executor) {
            if (CANCELLED == newStatus) {
                throw new IllegalArgumentException("нельзя перевести из статуса "
                        + this.getName()
                        + " в статус "
                        + newStatus.getName());
            }
            if (EXECUTED == newStatus) {
                wish.setFactExecuteDate(Instant.now());
            }
            wish.setStatus(newStatus);
        }
    },

    CANCELLED("ОТМЕНЕНО") {
        @Override
        public void changeStatus(Wish wish, Status newStatus, User executor) {
            throw new IllegalArgumentException("нельзя перевести из статуса " + this.getName() + " в иной статус");
        }
    },

    OPEN("ОТКРЫТО") {
        @Override
        public void changeStatus(Wish wish, Status newStatus, User executor) {
            if (EXECUTED == newStatus) {
                throw new IllegalArgumentException("нельзя перевести из статуса "
                        + this.getName()
                        + " в статус "
                        + newStatus.getName());
            }
            wish.setStatus(newStatus);
        }
    },

    EXECUTED("ИСПОЛНЕНО") {
        @Override
        public void changeStatus(Wish task, Status newStatus, User executor) {
            throw new IllegalArgumentException("нельзя перевести из статуса " + this.getName() + " в иной статус");
        }
    },

    READY("ГОТОВО") {
        @Override
        public void changeStatus(Wish task, Status newStatus, User executor) {
            throw new IllegalArgumentException("нельзя перевести из статуса " + this.getName() + " в иной статус");
        }
    };

    private final String name;

    Status(String name) {
        this.name = name;
    }

    public abstract void changeStatus(Wish wish, Status newStatus, User executor);

    public String getName() {
        return name;
    }
}
