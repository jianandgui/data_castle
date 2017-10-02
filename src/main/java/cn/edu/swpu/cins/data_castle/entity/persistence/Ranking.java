package cn.edu.swpu.cins.data_castle.entity.persistence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Ranking {
    private Integer id;
    private Integer teamId;
    private int score;


    public Ranking setId(Integer id) {
        this.id = id;
        return this;
    }

    public Ranking setTeamId(Integer teamId) {
        this.teamId = teamId;
        return this;
    }

    public Ranking setScore(int score) {
        this.score = score;
        return this;
    }
}
