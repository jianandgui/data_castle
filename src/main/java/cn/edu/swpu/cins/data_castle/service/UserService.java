package cn.edu.swpu.cins.data_castle.service;

import cn.edu.swpu.cins.data_castle.entity.dto.SignInUser;
import cn.edu.swpu.cins.data_castle.entity.dto.SignUp;
import cn.edu.swpu.cins.data_castle.entity.dto.UserSignResult;
import cn.edu.swpu.cins.data_castle.entity.persistence.UserInfo;
import cn.edu.swpu.cins.data_castle.exception.OperationFailureException;
import cn.edu.swpu.cins.data_castle.exception.UserException;

import javax.mail.MessagingException;
import javax.mail.Service;

public interface UserService {

    int insertUser(SignUp signUp, String code) throws MessagingException, UserException;
    int enableAccount(String mail, String token) throws OperationFailureException, UserException;
    UserSignResult userLogin(SignInUser signInUser,String captchaCode) throws UserException;
}
