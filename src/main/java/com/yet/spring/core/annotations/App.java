package com.yet.spring.core.annotations;

import com.yet.spring.core.annotations.beans.Client;
import com.yet.spring.core.annotations.beans.EventType;
import com.yet.spring.core.annotations.loggers.Event;
import com.yet.spring.core.annotations.loggers.EventLogger;
import com.yet.spring.core.annotations.spring.AppConfig;
import com.yet.spring.core.annotations.spring.LoggerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

import static com.yet.spring.core.annotations.beans.EventType.ERROR;
import static com.yet.spring.core.annotations.beans.EventType.INFO;

/**
 * Created by User on 19.07.2017.
 */
@Service
public class App {

    @Autowired
    private Client client;

    @Resource(name = "defaultLogger") //этот бин прописан методом defaultLogger() в классе LoggerConfig
    private EventLogger defaultLogger;

    @Resource(name = "loggerMap") //этот бин прописан методом loggerMap() в классе LoggerConfig
    private Map<EventType, EventLogger> loggers;

    App(Client client, EventLogger defaultLogger,
        Map<EventType, EventLogger> loggersMap) {
        this.client = client;
        this.defaultLogger = defaultLogger;
        this.loggers = loggersMap;
    }

    public static void main(String[] args) throws IOException {

//        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class, LoggerConfig.class);
// Можем контекст создавать через конструктор (см. сверху), передавая ему все файлы, помеченные @Configuration.
// А можно делать то же самое с помощью метода register, но надо не забыть вызвать !!! ctx.refresh(), чтобы обновить контекст
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(AppConfig.class, LoggerConfig.class);
        // сканирует пакет и загружает бины в контейнер
        ctx.scan("com.yet.spring.core.annotations");
        ctx.refresh();

        App app = (App) ctx.getBean("app");

        Event event = ctx.getBean(Event.class);
        app.logEvent(ERROR, event, "Some event for 2");
        event = ctx.getBean(Event.class);
        app.logEvent(INFO, event, "Some event for 2");
        event = ctx.getBean(Event.class);
        app.logEvent(ERROR, event, "Some event for 1");

        event = ctx.getBean(Event.class);
        app.logEvent(null, event, "Some event for 1");
        event = ctx.getBean(Event.class);
        app.logEvent(null, event, "Some event for 2");
        event = ctx.getBean(Event.class);
        app.logEvent(null, event, "Some event for 2");
        event = ctx.getBean(Event.class);
        app.logEvent(null, event, "Some event for 2");

        ctx.close();
    }

    private void logEvent(EventType type, Event event, String msg) throws IOException {
        String message = msg.replaceAll(client.getId(), client.getFullName());
        event.setMsg(message);

        EventLogger logger = loggers.get(type);//получаем из map loggers тот логгер, который соответствует ключу type(INFO-consoleEventLogger, ERROR-combinedEventLogger)
        if (logger == null) {
            logger = defaultLogger; //cacheFileEventLogger
        }
        logger.logEvent(event);
    }
}

