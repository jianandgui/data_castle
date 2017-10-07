package cn.edu.swpu.cins.data_castle.service.Impl;

import cn.edu.swpu.cins.data_castle.dao.UserDao;
import cn.edu.swpu.cins.data_castle.entity.dto.SignInUser;
import cn.edu.swpu.cins.data_castle.entity.dto.SignUp;
import cn.edu.swpu.cins.data_castle.entity.dto.UserSignResult;
import cn.edu.swpu.cins.data_castle.entity.persistence.UserInfo;
import cn.edu.swpu.cins.data_castle.exception.OperationFailureException;
import cn.edu.swpu.cins.data_castle.exception.UserException;
import cn.edu.swpu.cins.data_castle.service.*;
import cn.edu.swpu.cins.data_castle.utils.JedisAdapter;
import cn.edu.swpu.cins.data_castle.utils.RedisKey;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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



    @Override
    public int insertUser(SignUp signUp,String code) throws MessagingException {
        UserInfo userInfo = signUp.getUserInfo();

        String verifyKey = code;
        if (!jedisAdapter.exists(verifyKey)) {
            throw new UserException("请重新获取验证码", HttpStatus.FORBIDDEN);
        }
        if (!jedisAdapter.get(verifyKey).equals(signUp.getVerifyCode())) {
            throw new UserException("验证码错误请重新输入", HttpStatus.FORBIDDEN);
        }

        String key = RedisKey.getDatacastleRegister(userInfo.getMail());
        String token = UUID.randomUUID().toString();
        jedisAdapter.setex(key, 3, token);
        String subject = formatService.getSignUpSubject(userInfo.getUsername());
        String content = formatService.getSignUpContent(userInfo.getMail(), token);
        mailService.sendMail(userInfo.getMail(), subject, content);
        String pwd = userInfo.getPwd();
        userInfo.setPwd(passwordEncoderService.encode(pwd));
        return userDao.addUser(userInfo);
    }

    @Override
    public int enableAccount(String mail, String token) {
        String Tmail = urlCoderService.decode(mail);
        String Ttoken = urlCoderService.decode(token);
        String key = RedisKey.getDatacastleRegister(Tmail);
        if (mail == null || token == null) {
            throw new OperationFailureException("非法操作", HttpStatus.FORBIDDEN);
        }
        if (!jedisAdapter.exists(key)) {
            throw new OperationFailureException("链接失效，激活失败", HttpStatus.FORBIDDEN);
        }
        if (!jedisAdapter.get(key).equals(Ttoken)) {
            throw new OperationFailureException("token验证失败", HttpStatus.FORBIDDEN);
        }
        return userDao.updateUser(Tmail,0);
    }

    @Override
    public UserSignResult userLogin(SignInUser signInUser,String captchaCode) {

        String verifyKey = captchaCode;
        if (!jedisAdapter.exists(verifyKey)) {
            throw new UserException("请重新获取验证码", HttpStatus.FORBIDDEN);
        }
        if (!jedisAdapter.get(verifyKey).equals(signInUser.getVerifyCode())) {
            throw new UserException("验证码错误请重新输入", HttpStatus.FORBIDDEN);
        }
        UserInfo user = userDao.getUser(signInUser.getMail());
        if (user.getEnable() != 1) {
            throw new UserException("尚未激活，请激活后使用", HttpStatus.FORBIDDEN);
        }
        if (signInUser == null || user == null) {
            throw new UserException("没有该用户，请核对信息后登录", HttpStatus.FORBIDDEN);
        }
        if (!passwordEncoderService.match(signInUser.getPwd(), user.getPwd())) {
            throw new UserException("密码不正确", HttpStatus.FORBIDDEN);
        }
        //将用户加入缓存
        String token = UUID.randomUUID().toString();
        String encoderToken = passwordEncoderService.encode(token);
        String mail = user.getMail();
        String key = RedisKey.getDatacastleLogin(mail);
        jedisAdapter.setex(key, 86400, encoderToken);
        return new UserSignResult(token, mail);
    }
}
