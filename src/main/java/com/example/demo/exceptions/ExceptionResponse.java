package com.example.demo.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class ExceptionResponse {

    @Getter
    @Setter
    private Date timestamp;

    @Getter
    @Setter
    private String message;

    @Getter
    @Setter
    private String details;

    public ExceptionResponse(Date timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    @Override
    public String toString() {
        return "ExceptionResponse{" +
                "timestamp=" + timestamp +
                ", message='" + message + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
