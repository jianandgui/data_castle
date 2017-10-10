package cn.edu.swpu.cins.data_castle.entity.dto;

import cn.edu.swpu.cins.data_castle.entity.persistence.Ranking;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RankList {
    private int teamId;
    private String teamName;
    private double maxScore;
    private double lastScore;
    private int position;

    public RankList(Ranking ranking) {
        this.teamId = ranking.getTeamId();
        this.maxScore = ranking.getMaxScore();
        this.lastScore = ranking.getLastScore();
        this.teamName = ranking.getTeamName();
    }

    @Override
    public String toString() {
        return "RankList{" +
                "teamId=" + teamId +
                ", teamName='" + teamName + '\'' +
                ", maxScore=" + maxScore +
                ", lastScore=" + lastScore +
                ", position=" + position +
                '}';
    }
}
