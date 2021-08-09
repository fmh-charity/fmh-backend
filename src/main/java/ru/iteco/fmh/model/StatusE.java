package ru.iteco.fmh.model;

import ru.iteco.fmh.model.claim.Claim;
import ru.iteco.fmh.model.wish.Wish;

import java.time.LocalDateTime;

/**
 * Enum для статуса заявки и записки
 */
public enum StatusE {
    //открыта
    OPEN {
        @Override
        public void changeStatus(Claim claim) {
            claim.setStatus(StatusE.OPEN);

        }
        @Override
        public void changeStatus(Wish wish) {
            throw new IllegalArgumentException("Нельзя перевести в статус \"Активно\"");
        }
    },
    // в работе
    IN_PROGRESS{
        @Override
        public void changeStatus(Claim claim) {
            claim.setStatus(IN_PROGRESS);
            claim.setFactExecuteDate(null);
        }
        @Override
        public void changeStatus(Wish wish) {
            wish.setStatus(CANCELED);
            wish.setFactExecuteDate(null);
        }
    },

    // отменена
    CANCELED{
        @Override
        public void changeStatus(Claim claim) {
            if (IN_PROGRESS.equals(claim.getStatus())){
                throw new IllegalArgumentException("Нельзя перевести из статуса \"В работе\" в статус \"Отменена\"");
            }
            claim.setStatus(CANCELED);
            claim.setFactExecuteDate(null);
        }
        @Override
        public void changeStatus(Wish wish) {
            wish.setStatus(CANCELED);
            wish.setFactExecuteDate(null);
        }
    },
    //исполнена
    EXECUTED{
        @Override
        public void changeStatus(Claim claim) {
            if (OPEN.equals(claim.getStatus())){
                throw new IllegalArgumentException("Нельзя перевести из статуса \"Открыта\" в статус \"Исполнена\"");
            }
            claim.setStatus(StatusE.EXECUTED);
            claim.setFactExecuteDate(LocalDateTime.now().withNano(0));
        }
        @Override
        public void changeStatus(Wish wish) {
            wish.setStatus(StatusE.EXECUTED);
            wish.setFactExecuteDate(LocalDateTime.now().withNano(0));
        }
    };

    StatusE() {
    }

    public abstract void changeStatus(Claim claim);
    public abstract void changeStatus(Wish wish);

}
