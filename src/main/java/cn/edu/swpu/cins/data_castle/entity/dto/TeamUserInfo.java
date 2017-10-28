package cn.edu.swpu.cins.data_castle.entity.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TeamUserInfo {
    private String teamName;
    private String username;
    private String mail;
    private String schoolNumber;
    private double maxScore;
    private double lastScore;
}
