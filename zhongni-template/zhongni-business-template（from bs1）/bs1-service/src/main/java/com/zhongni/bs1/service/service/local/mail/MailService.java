package com.zhongni.bs1.service.service.local.mail;

public interface MailService {

    /**
     * 发送Html
     */
    void sendHtmlMail(String emailAddress, String verificationCode, String mailTemplateContent);
}
