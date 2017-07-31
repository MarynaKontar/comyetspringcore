package com.yet.spring.core.xml;

import com.yet.spring.core.xml.beans.Client;
import com.yet.spring.core.xml.beans.EventType;
import com.yet.spring.core.xml.loggers.Event;
import com.yet.spring.core.xml.loggers.EventLogger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.Map;

import static com.yet.spring.core.xml.beans.EventType.*;

/**
 * Created by User on 19.07.2017.
 */
public class App {
    private Client client;
    private Map<EventType, EventLogger> loggers;
//    private EventLogger eventLogger;
    private static ConfigurableApplicationContext ctx;


//    public App(Client client, EventLogger eventLogger, Map<EventType, EventLogger> loggers) {
//        this.client = client;
//        this.eventLogger = eventLogger;
//        this.loggers = loggers;
//    }

    public App(Client client, Map<EventType, EventLogger> loggers) {
        this.client = client;
        this.loggers = loggers;
    }

    public static void main(String[] args) throws IOException {
//        com.yet.spring.core.xml.App app = new com.yet.spring.core.xml.App();
//        app.client = new com.yet.spring.core.xml.beans.Client("1", "John Smith");
//        app.eventLogger = new com.yet.spring.core.xml.loggers.ConsoleEventLogger();
//        app.logEvent("Some event for user 1");

//        Контекст может быть считан не с одного .xml файла, а с нескольких, бины в них могут инжектить друг друга (тег ref).
//        Spring объеденит контекст
//        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml", "spring2.xml");


        //Если мы используем логгер CacheFileEventLogger, то нам надо, чтобы кеш сбрасывался в файл при завершении приложения.
        // Т.е. если размер кеша еще не достиг cacheSize, а мы закрываем приложение, то данные из кеша должны сбрасываюттся в
        // файл (для этого нужен метод destroy() в CacheFileEventLogger и не забыть прописать атрибут
        // destroy)-method для бина cacheFileEventLogger. Он (метод destroy())вызовется перед закрытием контекста.
        // Но для этого надо напрямую сказать контексту close, а ApplicationContext не содержит метод close.
        // Поэтому мы его заменяем на ConfigurableApplicationContext, в котором есть метод close и которій наследуется от ApplicationContext
        ctx = new ClassPathXmlApplicationContext("spring.xml", "spring2.xml");


        //может быть несколько дочерних web contexts (ctx), каждый из которых реализует отдельный функционал
        // и использует бины от родителей (ctx1)
//        ConfigurableApplicationContext ctx1 = new ClassPathXmlApplicationContext(
//                "spring.xml");
//        String[] xmls = {"spring2.xml"}; // logger прописан в spring2.xml ( <import resource="logger.xml"/>)
//        ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext(xmls,ctx1);

        App app = (App) ctx.getBean("app");
//        App app =  ctx.getBean("app", com.yet.spring.core.xml.App.class);
//        App app =  ctx.getBean(com.yet.spring.core.xml.App.class);
//        Event event1 = ctx.getBean("event", com.yet.spring.core.xml.loggers.Event.class);
//        Event event2 = ctx.getBean("event", com.yet.spring.core.xml.loggers.Event.class);
//        Event event3 = ctx.getBean("event", com.yet.spring.core.xml.loggers.Event.class);
//        Event event4 = ctx.getBean("event", com.yet.spring.core.xml.loggers.Event.class);
//        event1.setMsg("Some event for 2");
//        event2.setMsg("Some event for 1");
//        event3.setMsg("Some event for 2");
//        event4.setMsg("Some event for 2");
//        app.logEvent(event1);
//        app.logEvent(event2);
//        app.logEvent(event3);
//        app.logEvent(event4);


        app.logEvent(ERROR, "Some event for 2");
        app.logEvent(INFO, "Some event for 2");
        app.logEvent(ERROR, "Some event for 1" );
//        app.logEvent(INFO, "Some event for 1");
        app.logEvent(null, "Some event for 1");
        app.logEvent(null, "Some event for 2");
        app.logEvent(null, "Some event for 2");
        app.logEvent(null, "Some event for 2");


        ctx.close();
//


    }

//    private void logEvent(Event event) throws IOException {
//        String message = event.getMsg().replaceAll(client.getId(), client.getFullName());//если в event.getMsg()встретится id, заменяет его на FullName
//        event.setMsg(message);
//        eventLogger.logEvent(event);
//    }

    private void logEvent(EventType type, String msg) throws IOException {
        String message = msg.replaceAll(client.getId(), client.getFullName());
        EventLogger logger = loggers.get(type);
        if(logger == null) {
            logger = ctx.getBean("cacheFileEventLogger", com.yet.spring.core.xml.loggers.CacheFileEventLogger.class);
        }

        Event event = ctx.getBean("event", com.yet.spring.core.xml.loggers.Event.class);
        event.setMsg(message);
        logger.logEvent(event);
    }

}

