package com.yet.spring.core.annotations.utils;

import com.yet.spring.core.annotations.loggers.EventLogger;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Created by User on 28.07.2017.
 * Listener мониторит события и в методе onApplicationEvent прописываем, что делать, если произошло событие.
 * В данном случае просто выводим в консоль информацию о событии.
 * Если писать просто для ApplicationEvent, то доступа к контексту нет (getApplicationContext()). НО
 *Я могу написать отдельные мониторы на начала события(contextStartedEvent), конец(ContextStoppedEvent),
 * обновление(ContextRefreshEvent), закрытие(ContextClosedEvent)
 * т.е. отдельно на каждую часть события. И тогда есть доступ к контексту и всем его параметрам.
 * Можно, например при закрытии контекста вывести в консоль имена всех бинов
 *  @Component
    public class Monitor implements ApplicationListener<ContextClosedEvent>{
        @Override
        public void onApplicationEvent(ContextClosedEvent event) {
            System.out.println("Bean names: " + Arrays.toString(event.getApplicationContext().getBeanDefinitionNames()));
        }
    }
 */
@Component
public class Monitor implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("EventLogger bean names: " + Arrays.toString(event.getApplicationContext().getBeanNamesForType(EventLogger.class)));
    }
}

//    @Component
//    public class Monitor implements ApplicationListener<ApplicationEvent>{
//    @Override
//    public void onApplicationEvent(ApplicationEvent event) {
//        System.out.println(event.getClass().getSimpleName() + " > Source: " + event.getSource().toString() + "\n > TimeStamp: " + event.getTimestamp());
//    }
//}
