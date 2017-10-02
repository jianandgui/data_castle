package cn.edu.swpu.cins.data_castle.exception;

import org.springframework.http.HttpStatus;

public class OperationFailureException extends DataCastleException {
    public OperationFailureException(String message, HttpStatus status) {
        super(message, status);
    }
}
