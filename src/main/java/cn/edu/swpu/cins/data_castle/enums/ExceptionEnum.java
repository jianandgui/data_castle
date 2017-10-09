package cn.edu.swpu.cins.data_castle.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ExceptionEnum {

    ERROR_PRAM("参数错误"),
    ENABLE_FAILED("链接失效，激活失败"),
    VERIFY_FAILED("token验证失败"),
    NO_ENABLE("尚未激活，请激活后使用"),
    NO_USER("没有该用户，请核对信息后登录"),
    INTERNAL_ERROR("服务器内部错误"),
    FORBIDEN("队伍名字不符合规范"),
    REGISTER_ERROR("注册失败"),
    MAIL_SEND_ERROR("邮件发送失败"),
    ILLEAGE_OPERATION("非法操作"),
    FAILED_ENABLE("激活失败"),
    ERROR_PWD("密码不正确");
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
