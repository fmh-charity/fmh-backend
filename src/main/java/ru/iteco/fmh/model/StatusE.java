package ru.iteco.fmh.model;

import java.time.LocalDateTime;

/**
 * Enum для статуса заявки и записки
 */
public enum StatusE {
    //активна
    ACTIVE{
        @Override
        public void doActionDependsOfStatus(Claim claim) {
        }
        @Override
        public void doActionDependsOfStatus(Note note) {
        }
    },
    // отменена
    CANCELED{
        @Override
        public void doActionDependsOfStatus(Claim claim) {
            claim.setStatus(CANCELED);
            claim.setFactExecuteDate(null);
        }
        @Override
        public void doActionDependsOfStatus(Note note) {
            note.setStatus(CANCELED);
            note.setFactExecuteDate(null);
        }
    },
    //исполнена
    EXECUTED{
        @Override
        public void doActionDependsOfStatus(Claim claim) {
            claim.setStatus(StatusE.EXECUTED);
            claim.setFactExecuteDate(LocalDateTime.now().withNano(0));
        }
        @Override
        public void doActionDependsOfStatus(Note note) {
            note.setStatus(StatusE.EXECUTED);
            note.setFactExecuteDate(LocalDateTime.now().withNano(0));
        }
    };

    StatusE() {
    }

    public abstract void doActionDependsOfStatus(Claim claim);
    public abstract void doActionDependsOfStatus(Note note);

}
