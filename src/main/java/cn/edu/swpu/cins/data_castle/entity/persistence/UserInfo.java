package cn.edu.swpu.cins.data_castle.entity.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserInfo {
    private Integer id;
    private Integer teamId;
    private String username;
    private String pwd;
    private String mail;
    private int enable;

    public UserInfo setId(Integer id) {
        this.id = id;
        return this;
    }

    public UserInfo setTeamId(Integer teamId) {
        this.teamId = teamId;
        return this;
    }

    public UserInfo setUsername(String username) {
        this.username = username;
        return this;
    }

    public UserInfo setPwd(String pwd) {
        this.pwd = pwd;
        return this;
    }

    public UserInfo setMail(String mail) {
        this.mail = mail;
        return this;
    }

    public UserInfo setEnable(int enable) {
        this.enable = enable;
        return this;
    }
}
