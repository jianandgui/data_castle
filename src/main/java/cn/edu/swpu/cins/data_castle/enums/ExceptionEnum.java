package cn.edu.swpu.cins.data_castle.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ExceptionEnum {

    ERROR_PRAM("parameter is error"),
    ENABLE_FAILED("link lose effectiveness,Activing failed"),
    VERIFY_FAILED("token verify failed"),
    NO_ENABLE("you have to active you account"),
    NO_USER("no user"),
    INTERNAL_ERROR("server inner error"),
    FORBIDEN("your teamName "),
    REGISTER_ERROR("register failed"),
    MAIL_SEND_ERROR("mail send error"),
    ILLEAGE_OPERATION("illegal operation"),
    GET_CODE_ERROR("get verifyCode failed"),
    FAILED_ENABLE("active failed"),
    MAIL_USERD("you email has been used"),
    ERROR_PWD("password wrong");
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
