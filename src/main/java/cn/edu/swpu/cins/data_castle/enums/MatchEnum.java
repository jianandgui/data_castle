package cn.edu.swpu.cins.data_castle.enums;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor

public enum MatchEnum {
    BAN_WITH_NATCHED("you have to match with person who do not belong a team"),
    FILE_UPLOAD_SUCCESS("upload file success"),
    BAN_WITH_NOENABLE("you hava to match with person who has active");

    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
