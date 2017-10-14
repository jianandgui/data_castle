package cn.edu.swpu.cins.data_castle.enums;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public enum VerifyCodeEnum {
//    REPEATE_GETCODE("please input verifyCode again"),
    REPEATE_GETCODE("请重新输入验证码"),
    ERROR_CODE("verifyCode is wrong");

    private String Msg;

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }
}
