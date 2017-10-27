package cn.edu.swpu.cins.data_castle.controller;

import cn.edu.swpu.cins.data_castle.entity.dto.ExceptionResult;
import cn.edu.swpu.cins.data_castle.entity.dto.MatchTeam;
import cn.edu.swpu.cins.data_castle.enums.MatchEnum;
import cn.edu.swpu.cins.data_castle.exception.DataCastleException;
import cn.edu.swpu.cins.data_castle.exception.MatchException;
import cn.edu.swpu.cins.data_castle.service.MatchService;
import cn.edu.swpu.cins.data_castle.utils.JedisAdapter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("dataCastle/match")
@AllArgsConstructor
public class MatchController {

    private JedisAdapter jedisAdapter;
    private MatchService matchService;

    @PostMapping("team")
    public ResponseEntity<?> createTeam(@RequestBody MatchTeam matchTeam,@RequestHeader("dataCastleMail") String ownEmail) throws MatchException {
        try {
            return new ResponseEntity<>(matchService.addTeam(matchTeam,ownEmail), HttpStatus.OK);
        } catch (DataCastleException e) {
            return new ResponseEntity<>(new ExceptionResult(e.getMessage()), e.getStatus());
        }
    }

    @PostMapping("answer")
    public ResponseEntity<?> uploadFile(@RequestHeader("dataCastleMail") String mail, @RequestPart("answer") MultipartFile file) {
        try {
            matchService.saveFile(file, mail);
            return new ResponseEntity(new ExceptionResult(MatchEnum.FILE_UPLOAD_SUCCESS.getMsg()), HttpStatus.OK);
//            return new ResponseEntity("上传成功", HttpStatus.OK);
        } catch (DataCastleException e) {
            return new ResponseEntity<>(new ExceptionResult(e.getMessage()), e.getStatus());
        }
    }

    @GetMapping("rankList")
    public ResponseEntity<?> getRankList() {
        try {
            return new ResponseEntity<>(matchService.queryRankList(), HttpStatus.OK);
        } catch (DataCastleException e) {
            return new ResponseEntity<>(new ExceptionResult(e.getMessage()), e.getStatus());
        }
    }
}
