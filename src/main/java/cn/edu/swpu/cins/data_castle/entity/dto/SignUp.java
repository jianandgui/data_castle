package cn.edu.swpu.cins.data_castle.entity.dto;

import cn.edu.swpu.cins.data_castle.entity.persistence.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUp {
    private UserInfo userInfo;
    private String verifyCode;
}
