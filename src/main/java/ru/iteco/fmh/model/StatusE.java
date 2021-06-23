package ru.iteco.fmh.model;

import java.time.LocalDateTime;

/**
 * Enum для статуса заявки и записки
 */
public enum StatusE {
    //активна
    ACTIVE{
        @Override
        public void changeStatus(Claim claim) {
            throw new IllegalArgumentException("Нельзя перевести в статус \"Активно\"");
        }
        @Override
        public void changeStatus(Note note) {
            throw new IllegalArgumentException("Нельзя перевести в статус \"Активно\"");
        }
    },
    // отменена
    CANCELED{
        @Override
        public void changeStatus(Claim claim) {
            claim.setStatus(CANCELED);
            claim.setFactExecuteDate(null);
        }
        @Override
        public void changeStatus(Note note) {
            note.setStatus(CANCELED);
            note.setFactExecuteDate(null);
        }
    },
    //исполнена
    EXECUTED{
        @Override
        public void changeStatus(Claim claim) {
            claim.setStatus(StatusE.EXECUTED);
            claim.setFactExecuteDate(LocalDateTime.now().withNano(0));
        }
        @Override
        public void changeStatus(Note note) {
            note.setStatus(StatusE.EXECUTED);
            note.setFactExecuteDate(LocalDateTime.now().withNano(0));
        }
    };

    StatusE() {
    }

    public abstract void changeStatus(Claim claim);
    public abstract void changeStatus(Note note);

}
