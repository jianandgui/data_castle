package cn.edu.swpu.cins.data_castle.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ExceptionEnum {

    ERROR_PRAM("Parameter is illegal"),
    ENABLE_FAILED("Link failed and activation failed"),
    VERIFY_FAILED("Token verify failed"),
    NO_ENABLE("You have to activate your account"),
    NO_REGISTER("You can't team up with unregistered users"),
    NO_USER("No user"),
    INTERNAL_ERROR("Server Inner Error"),
    FORBIDEN("Your teamName "),
    REGISTER_ERROR("Register failed"),
    MAIL_SEND_ERROR("Send Mail Failed"),
    ILLEAGE_OPERATION("Illegal operation"),
    GET_CODE_ERROR("Get verifyCode failed"),
    FAILED_ENABLE("Activate failed"),
    MAIL_USERD("Your mailbox has been used"),
    FILE_UPLOAD_FAILED("Upload file failed"),
    REPEATE_NAME("The teamName has been used"),
    UPLOAD_FILE_LIMIT("Your submit is enough"),
    NO_MATCH("You can not upload before match with other"),
    ERROR_PWD("Password wrong");

    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
