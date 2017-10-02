package cn.edu.swpu.cins.data_castle.service;

public interface MailFormatService {
    String getSignUpSubject(String username);

    String getSignUpContent(String mail, String token);
}
