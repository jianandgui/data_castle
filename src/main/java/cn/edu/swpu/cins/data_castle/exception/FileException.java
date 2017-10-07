package cn.edu.swpu.cins.data_castle.exception;

import org.springframework.http.HttpStatus;

public class FileException extends DataCastleException {
    public FileException(String message, HttpStatus status) {
        super(message, status);
    }
}
