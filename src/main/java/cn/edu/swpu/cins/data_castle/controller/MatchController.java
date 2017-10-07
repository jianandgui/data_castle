package cn.edu.swpu.cins.data_castle.controller;

import cn.edu.swpu.cins.data_castle.entity.dto.MatchTeam;
import cn.edu.swpu.cins.data_castle.exception.MatchException;
import cn.edu.swpu.cins.data_castle.service.MatchService;
import cn.edu.swpu.cins.data_castle.utils.JedisAdapter;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dataCastle/match")
@AllArgsConstructor
public class MatchController {

    private JedisAdapter jedisAdapter;
    private MatchService matchService;

    @PostMapping("team")
    public ResponseEntity createTeam(@RequestBody MatchTeam matchTeam) throws MatchException {
        try {
            return new ResponseEntity(matchService.addTeam(matchTeam), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
}
