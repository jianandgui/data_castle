package cn.edu.swpu.cins.data_castle.entity.dto;

import cn.edu.swpu.cins.data_castle.entity.persistence.UserInfo;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUp {
    private UserInfo userInfo;
    private String verifyCode;
}
