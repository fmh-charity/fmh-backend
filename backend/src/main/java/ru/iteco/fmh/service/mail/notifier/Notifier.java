package ru.iteco.fmh.service.mail.notifier;

public interface Notifier<T extends NotifierAbstractContext> {
    void send(T context);
}