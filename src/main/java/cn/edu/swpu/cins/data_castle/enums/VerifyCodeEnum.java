package cn.edu.swpu.cins.data_castle.enums;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public enum VerifyCodeEnum {
//    REPEATE_GETCODE("please input verifyCode again"),
    REPEATE_GETCODE("Please input verifyCode again"),
    ERROR_CODE("VerifyCode is wrong");

    private String Msg;

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }
}
