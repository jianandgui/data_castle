package cn.edu.swpu.cins.data_castle.exception;

import org.springframework.http.HttpStatus;

public class IlleagalArgumentException extends DataCastleException {
    public IlleagalArgumentException(String message, HttpStatus status) {
        super(message, status);
    }
}
