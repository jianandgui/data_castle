package cn.edu.swpu.cins.data_castle.enums;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public enum VerifyCodeEnum {
    REPEATE_GETCODE("请重新获取验证码！"),
    ERROR_CODE("验证码错误，请重新输入！");

    private String Msg;

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }
}
