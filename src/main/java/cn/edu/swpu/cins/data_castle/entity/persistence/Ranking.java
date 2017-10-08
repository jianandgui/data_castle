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
    private String teamName;


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

    public Ranking setTeamName(String teamName) {
        this.teamName = teamName;
        return this;
    }

    @Override
    public String toString() {
        return "Ranking{" +
                "id=" + id +
                ", teamId=" + teamId +
                ", score=" + score +
                ", teamName='" + teamName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ranking)) return false;

        Ranking ranking = (Ranking) o;

        if (getScore() != ranking.getScore()) return false;
        if (!getId().equals(ranking.getId())) return false;
        if (!getTeamId().equals(ranking.getTeamId())) return false;
        return getTeamName().equals(ranking.getTeamName());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getTeamId().hashCode();
        result = 31 * result + getScore();
        result = 31 * result + getTeamName().hashCode();
        return result;
    }
}
