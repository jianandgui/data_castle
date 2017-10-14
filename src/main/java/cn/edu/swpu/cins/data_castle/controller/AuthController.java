package cn.edu.swpu.cins.data_castle.controller;

import cn.edu.swpu.cins.data_castle.entity.dto.SignInUser;
import cn.edu.swpu.cins.data_castle.entity.dto.SignUp;
import cn.edu.swpu.cins.data_castle.exception.MatchException;
import cn.edu.swpu.cins.data_castle.exception.UserException;
import cn.edu.swpu.cins.data_castle.service.UserService;
import cn.edu.swpu.cins.data_castle.utils.GetCode;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("dataCastle/user")
@AllArgsConstructor
public class AuthController {

    private UserService userService;
    private GetCode getCode;

    /**
     * 获取登录验证码接口
     *
     * @return
     */
    @GetMapping(value = "verifyCode")
    public ResponseEntity<?> getVerifyCodeForLogin(HttpServletResponse response) {
        try {
            String code = getCode.createCode(response);
            return new ResponseEntity<>(code, HttpStatus.OK);
        } catch (MatchException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }


    @PostMapping("signUp")
    public ResponseEntity<?> userRegister(@RequestBody SignUp signUp,@RequestHeader("captcha-code") String captchaCode) throws MessagingException {
        /*
        * 1、验证信息、验证码
        * 2、保存数据库
        * 3、在redis放激活码
        * 4、发送邮件确认链接
        *
        * */
        try {
            return new ResponseEntity<>(userService.insertUser(signUp, captchaCode), HttpStatus.OK);
        } catch (UserException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        } catch (MatchException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @PostMapping("signIn")
    public ResponseEntity<?> userLogin(@RequestBody SignInUser signInUser, @RequestHeader("captcha-code") String captchaCode) {
        try {
//            System.out.println("我是测试");
            return new ResponseEntity<>(userService.userLogin(signInUser, captchaCode), HttpStatus.OK);
        } catch (UserException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        } catch (MatchException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @GetMapping("enable")
    public ResponseEntity<?> userEnable(@RequestParam("mail") String mail,@RequestParam("token") String token) {
        try {
            return new ResponseEntity<>(userService.enableAccount(mail, token), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
}
