package cn.edu.swpu.cins.data_castle.service;

import cn.edu.swpu.cins.data_castle.entity.dto.SignInUser;
import cn.edu.swpu.cins.data_castle.entity.dto.SignUp;
import cn.edu.swpu.cins.data_castle.entity.dto.UserSignResult;
import cn.edu.swpu.cins.data_castle.entity.persistence.UserInfo;

import javax.mail.MessagingException;
import javax.mail.Service;

public interface UserService {

    int insertUser(SignUp signUp, String code) throws MessagingException;
    int enableAccount(String mail, String token);
    UserSignResult userLogin(SignInUser signInUser,String captchaCode);
}
