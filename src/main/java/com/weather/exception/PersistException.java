package com.weather.exception;

public class PersistException extends AppException {

    public PersistException(String entitiName, Throwable cause) {
        super(
                "PERSIST_ERROR",
                "Couldn't save" + entitiName,
                cause
        );
    }

    public PersistException(String entityName) {
        super("PERSIST_ERROR", "Couldn't save" + entityName);
    }


}
