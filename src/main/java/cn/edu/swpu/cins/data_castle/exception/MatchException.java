package cn.edu.swpu.cins.data_castle.exception;

import org.springframework.http.HttpStatus;

public class MatchException extends DataCastleException {
    public MatchException(String message, HttpStatus status) {
            super(message, status);
        }
    }

