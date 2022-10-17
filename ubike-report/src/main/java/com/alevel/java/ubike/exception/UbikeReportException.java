package com.alevel.java.ubike.exception;

public class UbikeReportException extends Exception {

    public UbikeReportException() {
    }

    public UbikeReportException(String message) {
        super(message);
    }

    public UbikeReportException(String message, Throwable cause) {
        super(message, cause);
    }

    public UbikeReportException(Throwable cause) {
        super(cause);
    }
}
