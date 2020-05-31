package com.app.exceptions;


import java.time.LocalDateTime;

public class MyException extends RuntimeException {
    private String exceptionMessage;
    private ExceptionInfo exceptionInfo;
    private LocalDateTime exceptionDateTime;

    public MyException(ExceptionInfo exceptionInfo, String exceptionMessage) {
        this.exceptionInfo = exceptionInfo;
        this.exceptionMessage = exceptionMessage;
        this.exceptionDateTime = LocalDateTime.now();
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public ExceptionInfo getExceptionInfo() {
        return exceptionInfo;
    }

    public LocalDateTime getExceptionDateTime() {
        return exceptionDateTime;
    }
}
