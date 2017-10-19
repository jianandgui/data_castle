package cn.edu.swpu.cins.data_castle.utils;

import cn.apiclub.captcha.Captcha;
import cn.apiclub.captcha.backgrounds.GradiatedBackgroundProducer;
import cn.apiclub.captcha.gimpy.FishEyeGimpyRenderer;
import cn.edu.swpu.cins.data_castle.enums.ExceptionEnum;
import cn.edu.swpu.cins.data_castle.exception.MatchException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
@AllArgsConstructor
public class GetCode {
    private JedisAdapter jedisAdapter;

    @Value("${data_castle.answer.captchaExpires}")
    private int captchaExpires ; //超时时间3min
    private static int captchaW = 200;
    private static int captchaH = 60;


    public String createCode(HttpServletResponse response) throws MatchException {
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
            return result;
        } catch (IOException e) {
            throw new MatchException(ExceptionEnum.GET_CODE_ERROR.getMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
