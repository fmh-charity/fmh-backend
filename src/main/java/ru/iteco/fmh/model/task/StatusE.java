package ru.iteco.fmh.model.task;

import java.time.LocalDateTime;

/**
 * Enum для статуса заявки и записки
 */
public enum StatusE {
    IN_PROGRESS {
        @Override
        public void changeStatus(Task task, StatusE newStatus) {
            switch (newStatus) {
                case CANCELLED:
                    throw new IllegalArgumentException("нельзя перевести из статуса \"В работе\" в статус \"Отменено\"");
                case EXECUTED:
                    task.setFactExecuteDate(LocalDateTime.now().withNano(0));
            }
            task.setStatus(newStatus);
        }
    },

    CANCELLED {
        @Override
        public void changeStatus(Task task, StatusE newStatus) {
            throw new IllegalArgumentException("нельзя перевести из статуса \"Отменено\" в иной статус");
        }
    },

    OPEN {
        @Override
        public void changeStatus(Task task, StatusE newStatus) {
            if (EXECUTED == newStatus) throw new IllegalArgumentException("нельзя перевести из статуса \"Открыто\" в статус \"Исполнено\"");
            task.setStatus(newStatus);
        }
    },

    EXECUTED {
        @Override
        public void changeStatus(Task task, StatusE newStatus) {
            throw new IllegalArgumentException("нельзя перевести из статуса \"Исполнено\" в иной статус");
        }
    };

    StatusE() {
    }

    public abstract void changeStatus(Task task, StatusE newStatus);
}
