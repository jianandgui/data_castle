package cn.edu.swpu.cins.data_castle.service.Impl;

import cn.edu.swpu.cins.data_castle.dao.UserDao;
import cn.edu.swpu.cins.data_castle.entity.dto.SignInUser;
import cn.edu.swpu.cins.data_castle.entity.dto.SignUp;
import cn.edu.swpu.cins.data_castle.entity.dto.UserSignResult;
import cn.edu.swpu.cins.data_castle.entity.persistence.UserInfo;
import cn.edu.swpu.cins.data_castle.enums.ExceptionEnum;
import cn.edu.swpu.cins.data_castle.enums.VerifyCodeEnum;
import cn.edu.swpu.cins.data_castle.exception.OperationFailureException;
import cn.edu.swpu.cins.data_castle.exception.UserException;
import cn.edu.swpu.cins.data_castle.service.*;
import cn.edu.swpu.cins.data_castle.utils.CreateKey;
import cn.edu.swpu.cins.data_castle.utils.JedisAdapter;
import cn.edu.swpu.cins.data_castle.utils.RedisKey;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{


    private UserDao userDao;
    private MailService mailService;
    private JedisAdapter jedisAdapter;
    private MailFormatService formatService;
    private URLCoderService urlCoderService;
    private PasswordEncoderService passwordEncoderService;
    private CreateKey createKey;

    private final static String LOGIN = "login";
    private final static String REGISTER = "register";


    @Override
    @Transactional
    public int insertUser(SignUp signUp,String code) throws MessagingException{
        checkSignUp(code, signUp.getVerifyCode());
        UserInfo userInfo = signUp.getUserInfo();
        String key = createKey.getKey(REGISTER,signUp.getUserInfo().getMail());
        String token = UUID.randomUUID().toString();
        jedisAdapter.setex(key, 3000, token);
        senMail(userInfo,token);
        String pwd = userInfo.getPwd();
        userInfo.setPwd(passwordEncoderService.encode(pwd));
        int count = userDao.addUser(userInfo);
        if (count != 1) {
            throw new UserException(ExceptionEnum.REGISTER_ERROR.getMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return 1;
    }

    public void senMail(UserInfo userInfo,String token)  {
        String subject = formatService.getSignUpSubject(userInfo.getUsername());
        String content = formatService.getSignUpContent(userInfo.getMail(), token);
        try {
            mailService.sendMail(userInfo.getMail(), subject, content);
        } catch (MessagingException e) {
            throw new UserException(ExceptionEnum.MAIL_SEND_ERROR.getMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void checkSignUp(String code,String verifyCode) {
        if (code == null) {
            throw new UserException(VerifyCodeEnum.ERROR_CODE.getMsg(), HttpStatus.FORBIDDEN);
        }
        String verifyKey = code;
        if (!jedisAdapter.exists(verifyKey)) {
            throw new UserException(VerifyCodeEnum.REPEATE_GETCODE.getMsg(), HttpStatus.FORBIDDEN);
        }
        if (!jedisAdapter.get(verifyKey).equals(verifyCode)) {
            throw new UserException(VerifyCodeEnum.ERROR_CODE.getMsg(), HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public int enableAccount(String mail, String token) {
        String Tmail = urlCoderService.decode(mail);
        String Ttoken = urlCoderService.decode(token);
        String key = createKey.getKey(REGISTER,Tmail);
        if (mail == null || token == null) {
            throw new OperationFailureException(ExceptionEnum.ILLEAGE_OPERATION.getMsg(), HttpStatus.FORBIDDEN);
        }
        if (!jedisAdapter.exists(key)) {
            throw new OperationFailureException(ExceptionEnum.ENABLE_FAILED.getMsg(), HttpStatus.FORBIDDEN);
        }
        if (!jedisAdapter.get(key).equals(Ttoken)) {
            throw new OperationFailureException(ExceptionEnum.ILLEAGE_OPERATION.getMsg(), HttpStatus.FORBIDDEN);
        }
        if (userDao.updateUser(Tmail, 0) != 1) {
            throw new UserException(ExceptionEnum.FAILED_ENABLE.getMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return 1;
    }

    @Override
    @Transactional
    public UserSignResult userLogin(SignInUser signInUser,String captchaCode) {

        String verifyKey = captchaCode;
        if (!jedisAdapter.exists(verifyKey)) {
            throw new UserException(VerifyCodeEnum.REPEATE_GETCODE.getMsg(),HttpStatus.FORBIDDEN);
        }
        if (!jedisAdapter.get(verifyKey).equals(signInUser.getVerifyCode())) {
            throw new UserException(VerifyCodeEnum.ERROR_CODE.getMsg(), HttpStatus.FORBIDDEN);
        }
        UserInfo user = userDao.getUser(signInUser.getMail());
        if (user.getEnable() != 1) {
            throw new UserException(ExceptionEnum.NO_ENABLE.getMsg(), HttpStatus.FORBIDDEN);
        }
        if (signInUser == null || user == null) {
            throw new UserException(ExceptionEnum.NO_USER.getMsg(), HttpStatus.FORBIDDEN);
        }
        if (!passwordEncoderService.match(signInUser.getPwd(), user.getPwd())) {
            throw new UserException(ExceptionEnum.ERROR_PWD.getMsg(), HttpStatus.FORBIDDEN);
        }
        //将用户加入缓存
        String token = UUID.randomUUID().toString();
        String encoderToken = passwordEncoderService.encode(token);
        String mail = user.getMail();
        String key = createKey.getKey(LOGIN,mail);
        jedisAdapter.setex(key, 8640000, encoderToken);
        return new UserSignResult(token, mail);
    }
}
