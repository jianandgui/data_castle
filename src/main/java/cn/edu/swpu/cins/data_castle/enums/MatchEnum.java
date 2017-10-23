package cn.edu.swpu.cins.data_castle.enums;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor

public enum MatchEnum {
    BAN_WITH_NATCHED("你不能和已经组队的人组队"),
    FILE_UPLOAD_SUCCESS("上传文件成功"),
    BAN_WITH_NOENABLE("你不能和还未激活账户的人组队，请提醒你的队友激活邮箱");

    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
