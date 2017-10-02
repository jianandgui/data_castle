package cn.edu.swpu.cins.data_castle.service;

import org.springframework.stereotype.Service;

public interface PasswordEncoderService {
    String encode(String password);
    boolean match(String password, String encodedPassword);
}
