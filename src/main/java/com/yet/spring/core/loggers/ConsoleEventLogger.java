package com.yet.spring.core.loggers;

/**
 * Created by User on 19.07.2017.
 */
public class ConsoleEventLogger implements EventLogger {

    public void logEvent(Event event){
        System.out.println(event.toString());
    }
}
