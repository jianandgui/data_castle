package cn.edu.swpu.cins.data_castle.exception;

import org.springframework.http.HttpStatus;

public class UserException extends DataCastleException {

    public UserException() {
    }

    public UserException(String message, HttpStatus status) {
        super(message, status);
    }
}
