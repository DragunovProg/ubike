package com.alevel.java.ubike.exceptions;

public class UbikeIngestException extends Exception {

    public UbikeIngestException() {
    }

    public UbikeIngestException(String message) {
        super(message);
    }

    public UbikeIngestException(String message, Throwable cause) {
        super(message, cause);
    }

    public UbikeIngestException(Throwable cause) {
        super(cause);
    }
}
