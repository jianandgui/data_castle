package cn.edu.swpu.cins.data_castle.entity.dto;

import cn.edu.swpu.cins.data_castle.entity.persistence.Ranking;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RankList {
    private int teamId;
    private int teamName;
    private int score;
    private int position;

    public RankList(Ranking ranking) {
        this.teamId = ranking.getTeamId();
        this.score = ranking.getScore();
    }
}
