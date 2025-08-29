package com.rekahdo.user_service.loggers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;

public abstract class ApiLogger {

    private final Logger logger;
    
    private enum LogType{
        SAVED,
        ADDED,
        EDITED,
        RETRIEVE_ALL,
        RETRIEVE_SINGLE,
        DELETE_ALL,
        DELETE_SINGLE,
    }

    public ApiLogger(Class<?> callingClass) {
        this.logger = LoggerFactory.getLogger(callingClass);
    }
    
    public void logSaved(String message){
        log(LogType.SAVED, message);
    }

    public void logAdded(String message){
        log(LogType.ADDED, message);
    }
    
    public void logEdited(String message){
        log(LogType.EDITED, message);
    }
    
    public void logGetAll(String message){
        log(LogType.RETRIEVE_ALL, message);
    }
    
    public void logGet(String message){
        log(LogType.RETRIEVE_SINGLE, message);
    }

    public void logDeletedAll(String message){
        log(LogType.DELETE_ALL, message);
    }
    
    public void logDeleted(String message){
        log(LogType.DELETE_SINGLE, message);
    }

    private void log(LogType logType, String message){
        logger.info("LOG {} <<< {} >>> TIME = {}", logType, message, Instant.now().toString());
    }

}
