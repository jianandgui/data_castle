package cn.edu.swpu.cins.data_castle.enums;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor

public enum MatchEnum {
    BAN_WITH_NATCHED("You can't team up with people who already have teams"),
    FILE_UPLOAD_SUCCESS("Upload file success"),
    BAN_WITH_NOENABLE("You can't team up with people who don't activate their accounts");

    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
