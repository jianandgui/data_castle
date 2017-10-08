package cn.edu.swpu.cins.data_castle.service;

import cn.edu.swpu.cins.data_castle.entity.dto.MatchTeam;
import cn.edu.swpu.cins.data_castle.entity.dto.RankList;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MatchService {
    int addTeam(MatchTeam matchTeam);

    boolean saveFile(MultipartFile file, String mail);

    List<RankList> queryRankList();
}
