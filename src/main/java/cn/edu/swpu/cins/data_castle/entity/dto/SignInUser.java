package cn.edu.swpu.cins.data_castle.entity.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SignInUser {
    private String pwd;
    private String mail;
    private String verifyCode;
}
