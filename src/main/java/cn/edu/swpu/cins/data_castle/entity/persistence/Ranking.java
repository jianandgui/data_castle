package cn.edu.swpu.cins.data_castle.entity.persistence;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ranking {
    private Integer id;
    private Integer teamId;
    private double maxScore;
    private String teamName;
    private double lastScore;

    @Override
    public String toString() {
        return "Ranking{" +
                "id=" + id +
                ", teamId=" + teamId +
                ", maxScore=" + maxScore +
                ", teamName='" + teamName + '\'' +
                ", lastScore=" + lastScore +
                '}';
    }
}
