package cn.edu.swpu.cins.data_castle.controller;

import cn.edu.swpu.cins.data_castle.entity.dto.MatchTeam;
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
    public ResponseEntity<?> createTeam(@RequestBody MatchTeam matchTeam) throws MatchException {
        try {
            return new ResponseEntity<>(matchService.addTeam(matchTeam), HttpStatus.OK);
        } catch (DataCastleException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @PostMapping("answer")
    public ResponseEntity<?> uploadFile(@RequestHeader("data-castle-mail") String mail, @RequestPart("answer") MultipartFile file) {
        try {
            return new ResponseEntity<>(matchService.saveFile(file, mail), HttpStatus.OK);
//            return new ResponseEntity("上传成功", HttpStatus.OK);
        } catch (DataCastleException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @GetMapping("rankList")
    public ResponseEntity<?> getRankList() {
        try {
            return new ResponseEntity<>(matchService.queryRankList(), HttpStatus.OK);
        } catch (DataCastleException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }
}
