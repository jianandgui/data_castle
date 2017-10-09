package cn.edu.swpu.cins.data_castle.service.Impl;

import cn.edu.swpu.cins.data_castle.dao.MatchDao;
import cn.edu.swpu.cins.data_castle.dao.UserDao;
import cn.edu.swpu.cins.data_castle.entity.dto.MatchTeam;
import cn.edu.swpu.cins.data_castle.entity.dto.RankList;
import cn.edu.swpu.cins.data_castle.entity.persistence.Ranking;
import cn.edu.swpu.cins.data_castle.entity.persistence.TeamInfo;
import cn.edu.swpu.cins.data_castle.entity.persistence.UserInfo;
import cn.edu.swpu.cins.data_castle.enums.ExceptionEnum;
import cn.edu.swpu.cins.data_castle.enums.MatchEnum;
import cn.edu.swpu.cins.data_castle.exception.FileException;
import cn.edu.swpu.cins.data_castle.exception.MatchException;
import cn.edu.swpu.cins.data_castle.service.MatchService;
import cn.edu.swpu.cins.data_castle.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchServiceImpl implements MatchService {
    private UserDao userDao;
    private MatchDao marchDao;
    @Value("${data_castle.answer.location}")
    private String location;
    private TimeService timeService;

    @Autowired
    public MatchServiceImpl(UserDao userDao, MatchDao marchDao, TimeService timeService) {
        this.userDao = userDao;
        this.marchDao = marchDao;
        this.timeService = timeService;
    }

    @Override
    @Transactional
    public int addTeam(MatchTeam matchTeam) {
        String teamName = matchTeam.getTeamName();
        List<String> mails = matchTeam.getTeamerMail();
        checkCreateTeam(matchTeam);
        TeamInfo teamInfo = new TeamInfo();
        teamInfo.setTeamName(teamName);
        int teamId = marchDao.saveTeam(teamInfo);
        if (teamId == 0) {
            throw new MatchException(ExceptionEnum.INTERNAL_ERROR.getMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        int modCount = 0;
        for (String mail : mails) {
            modCount+=userDao.updateUser(mail, teamId);
        }
        if (modCount != mails.size()) {
            throw new MatchException(ExceptionEnum.INTERNAL_ERROR.getMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return 1;
    }

    public void checkCreateTeam(MatchTeam matchTeam) {

        List<String> mails = matchTeam.getTeamerMail();
        if (mails.size() < 1 || mails.size() > 2) {
            throw new MatchException(ExceptionEnum.ERROR_PRAM.getMsg(), HttpStatus.BAD_REQUEST);
        }
        if (matchTeam.getTeamName() == null) {
            throw new MatchException(ExceptionEnum.FORBIDEN.getMsg(), HttpStatus.FORBIDDEN);
        }
        int joinedCount = 0;
        int noEnableCount = 0;
        UserInfo user;
        for (String mail : mails) {
            user = userDao.getUser(mail);
            if (user.getTeamId() != 0) {
                joinedCount++;
            }
            if (user.getEnable() == 0) {
                noEnableCount++;
            }
        }
        if (joinedCount != 0) {
            throw new MatchException(MatchEnum.BAN_WITH_NATCHED.getMsg(), HttpStatus.FORBIDDEN);
        }
        if (noEnableCount != 0) {
            throw new MatchException(MatchEnum.BAN_WITH_NOENABLE.getMsg(), HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public boolean saveFile(MultipartFile multipartFile, String mail) {
        int teamId = userDao.getUser(mail).getTeamId();

//        String path = checkDir(teamId);
        String path = location;
        String fileName = teamId + "_answer";
        path += "/" + fileName;
        File file = new File(path);
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

   /* private String checkDir(int teamId) {
        String path = location + "/" + teamId;
        File dir = new File(path);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                throw new FileException("create dir failed", HttpStatus.BAD_REQUEST);
            }
        }
        return path;
    }*/

    @Override
    public List<RankList> queryRankList() {
        List<Ranking> rankingList = marchDao.selectAll();
        List<RankList> rankLists = getRankList(rankingList);
        int i = 0;
        for (RankList rank : rankLists) {
            rank.setPosition(++i);
        }
        return rankLists;
    }

    public List<RankList> getRankList(List<Ranking> rankingList) {
        return rankingList.stream()
                .map(RankList::new)
                .sorted(Comparator.comparing(RankList::getScore).reversed())
                .collect(Collectors.toList());
    }
}
