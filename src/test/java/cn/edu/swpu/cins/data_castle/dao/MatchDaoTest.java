package cn.edu.swpu.cins.data_castle.dao;

import cn.edu.swpu.cins.data_castle.entity.persistence.TeamInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MatchDaoTest {

    @Autowired
    private MatchDao matchDao;

    @Test
    public void saveTeam() throws Exception {
        TeamInfo teamInfo = new TeamInfo();
        teamInfo.setTeamName("test");
        int id = matchDao.saveTeam(teamInfo);
        System.out.println(teamInfo.getId());
    }

    @Test
    public void selectAll() throws Exception {
        System.out.println(matchDao.selectAll().toString());

    }

    @Test
    public void selectByTeamId() throws Exception{
        System.out.println(matchDao.selectByTeamId(19));
    }


}