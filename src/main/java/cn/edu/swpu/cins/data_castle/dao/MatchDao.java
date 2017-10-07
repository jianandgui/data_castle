package cn.edu.swpu.cins.data_castle.dao;

import cn.edu.swpu.cins.data_castle.entity.persistence.TeamInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface MatchDao {

    /*public final static String TABLE = "team_info";
    public final static String FIELD = "team_name";*/

//    @Insert("INSERT INTO" + TABLE + "(" + FIELD + ")" + "VALUES(#{teamInfo})")
    int saveTeam(TeamInfo teamInfo);

}
