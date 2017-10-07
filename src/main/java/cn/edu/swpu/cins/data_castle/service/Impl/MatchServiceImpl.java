package cn.edu.swpu.cins.data_castle.service.Impl;

import cn.edu.swpu.cins.data_castle.dao.MatchDao;
import cn.edu.swpu.cins.data_castle.dao.UserDao;
import cn.edu.swpu.cins.data_castle.entity.dto.MatchTeam;
import cn.edu.swpu.cins.data_castle.entity.persistence.TeamInfo;
import cn.edu.swpu.cins.data_castle.entity.persistence.UserInfo;
import cn.edu.swpu.cins.data_castle.exception.MatchException;
import cn.edu.swpu.cins.data_castle.service.MatchService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MatchServiceImpl implements MatchService {
    private UserDao userDao;
    private MatchDao marchDao;
    @Override
    public int addTeam(MatchTeam matchTeam) {
        List<String> mails = matchTeam.getTeamerMail();
        if (mails.size() > 2) {
            throw new MatchException("参数错误", HttpStatus.BAD_REQUEST);
        }
        String teamName = matchTeam.getTeamName();
        int joinCount = 0;
        int noEnableCount = 0;
        UserInfo user = null;
        for (String mail : mails) {
            user = userDao.getUser(mail);
            if (user.getTeamId()!=null) {
                joinCount++;
            }
            if (user.getEnable() == 0) {
                noEnableCount++;
            }
        }
        if (joinCount != 0) {
            throw new MatchException("不能与已经组队的人组队", HttpStatus.BAD_REQUEST);
        }
        if (noEnableCount != 0) {
            throw new MatchException("组队所有人都必须激活自己的账号", HttpStatus.BAD_REQUEST);
        }
        TeamInfo teamInfo = new TeamInfo();
        teamInfo.setTeamName(teamName);
        int teamId = marchDao.saveTeam(teamInfo);
        for (String mail : mails) {
            userDao.updateUser(mail, teamId);
        }
        return 1;
    }
}