package cn.edu.swpu.cins.data_castle.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ExceptionEnum {

    ERROR_PRAM("非法参数"),
    ENABLE_FAILED("链接失效，激活失败"),
    VERIFY_FAILED("token验证失败"),
    NO_ENABLE("你必须先激活你的帐号"),
    NO_REGISTER("你不能和没有注册的人组队"),
    NO_USER("没有该用户"),
    INTERNAL_ERROR("服务器内部错误"),
    FORBIDEN("Your teamName "),
    REGISTER_ERROR("注册失败"),
    MAIL_SEND_ERROR("发送邮件失败"),
    ILLEAGE_OPERATION("非法操作"),
    GET_CODE_ERROR("获取验证码失败"),
    FAILED_ENABLE("激活失败"),
    MAIL_USERD("你填写的邮箱已经被占用"),
    FILE_UPLOAD_FAILED("上传邮件失败"),
    REPEATE_NAME("该队伍名字已经被占用"),
    UPLOAD_FILE_LIMIT("你的上传次数已经达到上限"),
    NO_MATCH("在没有组队之前不能上传答案"),
    ERROR_PWD("密码错误");

    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
