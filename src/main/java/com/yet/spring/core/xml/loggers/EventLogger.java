package com.yet.spring.core.xml.loggers;

import java.io.IOException;

/**
 * Created by User on 19.07.2017.
 */
public interface EventLogger {
   void logEvent(Event event) throws IOException;
}
