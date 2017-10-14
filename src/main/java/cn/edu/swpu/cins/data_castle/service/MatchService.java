package cn.edu.swpu.cins.data_castle.service;

import cn.edu.swpu.cins.data_castle.entity.dto.MatchTeam;
import cn.edu.swpu.cins.data_castle.entity.dto.RankList;
import cn.edu.swpu.cins.data_castle.exception.FileException;
import cn.edu.swpu.cins.data_castle.exception.MatchException;
import cn.edu.swpu.cins.data_castle.exception.UserException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MatchService {
    int addTeam(MatchTeam matchTeam) throws MatchException;

    void saveFile(MultipartFile file, String mail) throws FileException, UserException;

    List<RankList> queryRankList() throws UserException;
}
