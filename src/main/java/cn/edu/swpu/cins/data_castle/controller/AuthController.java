package cn.edu.swpu.cins.data_castle.controller;

import cn.apiclub.captcha.Captcha;
import cn.apiclub.captcha.backgrounds.GradiatedBackgroundProducer;
import cn.apiclub.captcha.gimpy.FishEyeGimpyRenderer;
import cn.edu.swpu.cins.data_castle.entity.dto.SignInUser;
import cn.edu.swpu.cins.data_castle.entity.dto.SignUp;
import cn.edu.swpu.cins.data_castle.entity.persistence.UserInfo;
import cn.edu.swpu.cins.data_castle.service.UserService;
import cn.edu.swpu.cins.data_castle.utils.JedisAdapter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("dataCastle/user")
@AllArgsConstructor
public class AuthController {


    private UserService userService;
    private JedisAdapter jedisAdapter;


    private static int captchaExpires = 3 * 60; //超时时间3min
    private static int captchaW = 200;
    private static int captchaH = 60;

    /**
     * 获取登录验证码接口
     *
     * @return
     */
    //, produces = MediaType.IMAGE_PNG_VALUE
    @GetMapping(value = "/getVerifyCode")
    public ResponseEntity getVerifyCodeForLogin(HttpServletResponse response) {

        BASE64Encoder encoder = new BASE64Encoder();

        String uuid = UUID.randomUUID().toString();
        Captcha captcha = new Captcha.Builder(captchaW, captchaH)
                .addText().addBackground(new GradiatedBackgroundProducer(Color.orange, Color.white))
                .gimp(new FishEyeGimpyRenderer())
                .build();
        System.out.println("验证码为    " + captcha.getAnswer());
        //将验证码以<key,value>形式缓存到redis
        jedisAdapter.setex(uuid, captchaExpires, captcha.getAnswer());
        //将验证码key，及验证码的base64编码返回
        response.addHeader("captcha-code", uuid);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        try {
            ImageIO.write(captcha.getImage(), "png", bao);

            byte[] bytes = bao.toByteArray();
            String result = encoder.encode(bytes);
//            response.getWriter().write(result);
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (IOException e) {
            return null;
        }
    }


    @PostMapping("signUp")
    public ResponseEntity userRegister(@RequestBody SignUp signUp,@RequestHeader("captcha-code") String captchaCode) throws MessagingException {
        /*
        * 1、验证信息、验证码
        * 2、保存数据库
        * 3、在redis放激活码
        * 4、发送邮件确认链接
        *
        * */
        return new ResponseEntity(userService.insertUser(signUp,captchaCode), HttpStatus.OK);
    }

    @PostMapping("signIn")
    public ResponseEntity userLogin(@RequestBody SignInUser signInUser, @RequestHeader("captcha-code") String captchaCode) {
        userService.userLogin(signInUser, captchaCode);
        return new ResponseEntity("", HttpStatus.OK);
    }

    @GetMapping("enable")
    public ResponseEntity userEnable(@RequestParam("mail") String mail,@RequestParam("token") String token) {
        try {
            return new ResponseEntity(userService.enableAccount(mail, token), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
}
