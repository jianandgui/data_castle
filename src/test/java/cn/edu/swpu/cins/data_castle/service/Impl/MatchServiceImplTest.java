package cn.edu.swpu.cins.data_castle.service.Impl;

import cn.edu.swpu.cins.data_castle.entity.dto.RankList;
import cn.edu.swpu.cins.data_castle.service.MatchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MatchServiceImplTest {

    @Autowired
    private MatchService matchService;

    @Test
    public void queryRankList() throws Exception {

//        System.out.println(matchService.queryRankList().toString());
        for (RankList rankList : matchService.queryRankList()) {
            System.out.println(rankList.toString());
        }
    }

}