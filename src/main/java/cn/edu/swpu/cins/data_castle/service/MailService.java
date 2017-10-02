package cn.edu.swpu.cins.data_castle.service;

import javax.mail.MessagingException;

public interface MailService {

    void sendMail(String to, String subject, String txt) throws MessagingException;
}
