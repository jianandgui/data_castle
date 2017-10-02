package cn.edu.swpu.cins.data_castle.controller;

import cn.edu.swpu.cins.data_castle.entity.persistence.UserInfo;
import cn.edu.swpu.cins.data_castle.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@RequestMapping("dataCastle")
@AllArgsConstructor
public class UserController {


    private UserService userService;



    @PostMapping("user")
    public ResponseEntity userRegister(@RequestBody UserInfo userInfo) throws MessagingException {
        /*
        * 1、验证信息
        * 2、保存数据库
        * 3、在redis放激活码
        * 4、发送邮件确认链接
        *
        * */
        return new ResponseEntity(userService.insertUser(userInfo), HttpStatus.OK);
    }

    @GetMapping("user/enable")
    public ResponseEntity userEnable(@RequestParam("mail") String mail,@RequestParam("token") String token) {
        try {
            return new ResponseEntity(userService.enableAccount(mail, token), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

}
