package ru.iteco.fmh.service.mail.notifier;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SendEmailNotifierContext extends NotifierAbstractContext {

    private String toAddress; //кому
    private String fromAddress; //от кого
    private String senderName; //организация отправитель
    private String subject; //тема письма
    private String content; //текст письма в формате html

}
