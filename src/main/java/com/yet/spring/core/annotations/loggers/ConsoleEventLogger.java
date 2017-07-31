package com.yet.spring.core.annotations.loggers;

import org.springframework.stereotype.Component;

/**
 * Created by User on 19.07.2017.
 */
@Component("consoleEventLogger")//можно писать имя бина, а можно и нет, все равно по умолчанию будет имя как название файла, но с маленькой буквы
public class ConsoleEventLogger implements EventLogger {

    public void logEvent(Event event){
        System.out.println(event.toString());
    }
}
