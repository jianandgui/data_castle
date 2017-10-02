package cn.edu.swpu.cins.data_castle.exception;

import org.springframework.http.HttpStatus;

public class RankingException extends DataCastleException {


    public RankingException(String message, HttpStatus status) {
        super(message, status);
    }
}
