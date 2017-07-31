package com.yet.spring.core.annotations.loggers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

/**
 * Нужен для записи event в файл, но не сразу, а после заполнения кеша
 */
@Component
//@Component("cacheEventLogger")
public class CacheFileEventLogger extends FileEventLogger {

    @Value("${cache.size:3}") // не забывать ${...:...}. А то получается String, а нужно int
    private int cacheSize;

    private List<Event> cache;

    public CacheFileEventLogger() {
    }

    public CacheFileEventLogger(String filename, int cacheSize) {
        super(filename);
        this.cacheSize = cacheSize;
    }

    @PostConstruct
    public void initCache() {
        this.cache = new ArrayList<Event>(cacheSize);
    }

    //делает так, чтобы кеш сбрасывался в файл при завершении приложения. Т.е. если размер кеша еще не достиг cacheSize,
    // а мы закрываем приложение, то данные из кеша сбрасываются в файл (для этого надо не забыть прописать атрибут
    // destroy-method для бина cacheFileEventLogger или поставаить аннотацию @PreDestroy. Он вызовется перед закрытием контекста. Но для этого надо
    // напрямую сказать контексту close, а ApplicationContext не содержит метод close. Поэтому мы его заменяем на
//    ConfigurableApplicationContext, в котором есть метод close и который наследуется от ApplicationContext
    @PreDestroy
    public void destroy() {
        if (!cache.isEmpty() && cache != null)
            writeEventFromCache();
        cache.clear();//надо обязательно clear, иначе event будет записіваться два раза в файл, т.к.  destroy()
        // будет вызываться 2 раза для CacheFileEventLogger бина. Потому что в LoggerConfig есть @Bean defaultLogger(),
        // и есть еще сам бин этого класса cacheFileEventLogger (c @Component)

    }

    @Override
    public void logEvent(Event event) {
//        if(cache==null || cache.isEmpty()) cache = new ArrayList<>(cacheSize);
        cache.add(event);
        if (cache.size() == cacheSize) {
            writeEventFromCache();
            cache.clear();
        }
    }

    private void writeEventFromCache() {
        cache.forEach(super::logEvent);
//        cache.stream().forEach(super::logEvent);
//        cache.forEach(event ->super.logEvent(event) );
//        for (Event event : cache) {
//            cache.forEach(super::logEvent);
//        }
    }

    public int getCacheSize() {
        return cacheSize;
    }

    public void setCacheSize(int cacheSize) {
        this.cacheSize = cacheSize;
    }

    public List<Event> getCache() {
        return cache;
    }

    public void setCache(List<Event> cache) {
        this.cache = cache;
    }
}
