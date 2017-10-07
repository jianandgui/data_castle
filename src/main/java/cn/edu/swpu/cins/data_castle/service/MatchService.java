package cn.edu.swpu.cins.data_castle.service;

import cn.edu.swpu.cins.data_castle.entity.dto.MatchTeam;
import org.springframework.web.multipart.MultipartFile;

public interface MatchService {
    int addTeam(MatchTeam matchTeam);

    boolean saveFile(MultipartFile file, String mail);
}
