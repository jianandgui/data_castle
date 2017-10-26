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
import cn.edu.swpu.cins.data_castle.exception.DataCastleException;
import cn.edu.swpu.cins.data_castle.exception.FileException;
import cn.edu.swpu.cins.data_castle.exception.MatchException;
import cn.edu.swpu.cins.data_castle.exception.UserException;
import cn.edu.swpu.cins.data_castle.service.MatchService;
import cn.edu.swpu.cins.data_castle.service.TimeService;
import cn.edu.swpu.cins.data_castle.utils.JedisAdapter;
import cn.edu.swpu.cins.data_castle.utils.RedisKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
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
    @Value("${data_castle.answer.limitTime}")
    private int limitTime;

    @Autowired
    private JedisAdapter jedisAdapter;

    @Autowired
    public MatchServiceImpl(UserDao userDao, MatchDao marchDao, TimeService timeService) {
        this.userDao = userDao;
        this.marchDao = marchDao;
        this.timeService = timeService;
    }

    @Override
    @Transactional(rollbackFor = {SQLException.class, DataCastleException.class,RuntimeException.class})
    public int addTeam(MatchTeam matchTeam) throws MatchException {
        String teamName = matchTeam.getTeamName();
        List<String> mails = matchTeam.getTeamerMail();
        checkCreateTeam(matchTeam);
        TeamInfo teamInfo = new TeamInfo();
        teamInfo.setTeamName(teamName);
        checkTeamSaveToDB(teamInfo, mails);
        return 1;
    }

    public void checkTeamSaveToDB(TeamInfo teamInfo,List<String> mails) throws MatchException {
        marchDao.saveTeam(teamInfo);
        int teamId = teamInfo.getId();
        if (teamId == 0) {
            throw new MatchException(ExceptionEnum.INTERNAL_ERROR.getMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        int modCount = 0;
        for (String mail : mails) {
            modCount += userDao.updateUser(mail, teamId);
        }
        if (modCount != mails.size()) {
            throw new MatchException(ExceptionEnum.INTERNAL_ERROR.getMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void checkCreateTeam(MatchTeam matchTeam) throws MatchException {
        List<String> mails = matchTeam.getTeamerMail();
        checkMail(mails, matchTeam);
        checkToMatch(mails);
    }

    public void checkToMatch(List<String> mails) throws MatchException {
        UserInfo user;
        int joinedCount = 0;
        int noEnableCount = 0;
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

    public void checkMail(List<String> mails,MatchTeam matchTeam) throws MatchException {
        boolean isAllExist = true;
        for (String mail : mails) {
            if (userDao.getUser(mail) == null) {
                isAllExist = false;
            }
        }
        if (marchDao.selectByName(matchTeam.getTeamName()) != null) {
            throw new MatchException(ExceptionEnum.REPEATE_NAME.getMsg(), HttpStatus.FORBIDDEN);
        }

        if (!isAllExist) {
            throw new MatchException(ExceptionEnum.NO_REGISTER.getMsg(), HttpStatus.FORBIDDEN);
        }
        if (mails.size() < 1 || mails.size() > 2) {
            throw new MatchException(ExceptionEnum.ERROR_PRAM.getMsg(), HttpStatus.BAD_REQUEST);
        }
        if (matchTeam.getTeamName() == null) {
            throw new MatchException(ExceptionEnum.FORBIDEN.getMsg(), HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public void saveFile(MultipartFile multipartFile, String mail) throws FileException, UserException {

        int teamId = userDao.getUser(mail).getTeamId();
        checkTeamId(teamId);
        //checkUploadCount(teamId);
        String path = location;
        String fileName = teamId + "_answer.csv";
        path += "/" + fileName;
        File file = new File(path);
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            throw  new FileException(ExceptionEnum.FILE_UPLOAD_FAILED.getMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void checkTeamId(int teamId) throws FileException {
        if (teamId == 0) {
            throw new FileException(ExceptionEnum.NO_MATCH.getMsg(), HttpStatus.FORBIDDEN);
        }
    }

    public void checkUploadCount(int teamId) throws FileException {
        String key = RedisKey.getUploadCount(teamId);
        if (!jedisAdapter.exists(key)) {
            jedisAdapter.setex(key, limitTime, String.valueOf(1));
        }else {
            int remainTime = (int) jedisAdapter.getTime(key);
            int uploadCount = Integer.parseInt(jedisAdapter.get(key));
            if (uploadCount > 1) {
                throw new FileException(ExceptionEnum.UPLOAD_FILE_LIMIT.getMsg(), HttpStatus.FORBIDDEN);
            }
            uploadCount++;
            jedisAdapter.setex(key,remainTime, String.valueOf(uploadCount));
        }
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
    public List<RankList> queryRankList() throws UserException {
        List<Ranking> rankingList;
        try {
            rankingList = marchDao.selectAll();
        } catch (Exception e) {
            throw new UserException(ExceptionEnum.INTERNAL_ERROR.getMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
                .sorted(Comparator.comparing(RankList::getMaxScore).reversed())
                .collect(Collectors.toList());
    }
}
