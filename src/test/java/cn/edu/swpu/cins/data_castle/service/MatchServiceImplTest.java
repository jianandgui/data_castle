package cn.edu.swpu.cins.data_castle.service;

import cn.edu.swpu.cins.data_castle.dao.MatchDao;
import cn.edu.swpu.cins.data_castle.dao.UserDao;
import cn.edu.swpu.cins.data_castle.entity.dto.MatchTeam;
import cn.edu.swpu.cins.data_castle.entity.persistence.TeamInfo;
import cn.edu.swpu.cins.data_castle.entity.persistence.UserInfo;
import cn.edu.swpu.cins.data_castle.service.Impl.MatchServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static sun.nio.cs.Surrogate.is;

@RunWith(MockitoJUnitRunner.class)
public class MatchServiceImplTest {

    @Mock
    private MatchDao marchDao;
    @Mock
    private UserDao userDao;
    private MatchService matchService;

    @Before
    public void setUp() throws Exception {
        matchService = new MatchServiceImpl(userDao,marchDao);
    }

    @Test
    public void test_marchTeam_success() {
        MatchTeam matchTeam = mock(MatchTeam.class);
        UserInfo user1 = mock(UserInfo.class);
        TeamInfo teamInfo = mock(TeamInfo.class);
        when(userDao.getUser(anyString())).thenReturn(user1);
        when(user1.getTeamId()).thenReturn(1);
        when(user1.getEnable()).thenReturn(1);
        when(userDao.updateUser(anyString(),anyInt())).thenReturn(1);
        when(marchDao.saveTeam(teamInfo)).thenReturn(1);
        assertThat(String.valueOf(matchService.addTeam(matchTeam)),is(1));
        verify(userDao).updateUser(anyString(), anyInt());
        verify(userDao).getUser(anyString());
        verify(marchDao).saveTeam(teamInfo);
    }
}
