package cn.edu.swpu.cins.data_castle.entity.persistence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TeamInfo {
    private Integer id;
    private String teamName;

    public TeamInfo setId(Integer id) {
        this.id = id;
        return this;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}
