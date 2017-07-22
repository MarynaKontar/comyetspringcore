package com.yet.spring.core.loggers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Нужен для записи event в файл, но не сразу, а после заполнения кеша
 */
public class CacheFileEventLogger extends FileEventLogger{

private int cacheSize;
private List<Event> cache;

    public CacheFileEventLogger(String filename, int cacheSize) {
        super(filename);
        this.cacheSize = cacheSize;
    }

    @Override
    public void logEvent(Event event)  {
        if(cache==null || cache.isEmpty()) cache = new ArrayList<>();
        cache.add(event);
        if (cache.size()>cacheSize){
            writeEventFromCache();
            cache.clear();
        }
    }

    //делает так, чтобы кеш сбрасывался в файл при завершении приложения. Т.е. если размер кеша еще не достиг cacheSize,
    // а мы закрываем приложение, то данные из кеша сбрасываются в файл (для этого надо не забыть прописать атрибут
    // destroy)-method для бина cacheFileEventLogger. Он вызовется перед закрытием контекста. Но для этого надо
    // напрямую сказать контексту close, а ApplicationContext не содержит метод close. Поэтому мы его заменяем на
//    ConfigurableApplicationContext, в котором есть метод close и которій наследуется от ApplicationContext

    public void destroy(){
        if (!cache.isEmpty())
            writeEventFromCache();
    }

    private void writeEventFromCache() {
        cache.forEach(super::logEvent);
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
