package com.yet.spring.core.annotations.loggers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * Created by User on 21.07.2017.
 */
@Component
public class CombinedEventLogger implements EventLogger {

    @Resource(name = "combinedLoggers")
    Collection<EventLogger> loggers;

//    public CombinedEventLogger(List<EventLogger> loggers) {
//        this.loggers = loggers;
//    }

    @Override
    public void logEvent(Event event) throws IOException {
        for (EventLogger logger : loggers) {
            logger.logEvent(event);
        }
    }

}

/**
 * То,что прописівали в logger.xml как
 *     <bean id="combinedEventLogger" class="com.yet.spring.core.xml.loggers.CombinedEventLogger">
 <constructor-arg>
 <list>
 <ref bean="consoleEventLogger"/>
 <ref bean="fileEventLogger"/>
 </list>
 </constructor-arg>
 </bean>
 здесь прописываем как @Resource(name="combinedLoggers")
 А combinedLoggers это имя бина, который создаем в LoggerConfig
 */
