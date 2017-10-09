package cn.edu.swpu.cins.data_castle.enums;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor

public enum MatchEnum {
    BAN_WITH_NATCHED("不能与已经组队的组队！"),
    BAN_WITH_NOENABLE("不能与还未激活帐号的人组队！");

    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
