package cn.edu.swpu.cins.data_castle.exception;

import org.springframework.http.HttpStatus;

public class DataCastleException extends Exception{

    protected HttpStatus status;
    public DataCastleException() {
        super();
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public DataCastleException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

}
