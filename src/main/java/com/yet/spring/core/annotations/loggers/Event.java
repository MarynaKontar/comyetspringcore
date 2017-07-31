package com.yet.spring.core.annotations.loggers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.text.DateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by User on 20.07.2017.
 * scope="prototype" - каждый раз вызывая getBean() будем получать новый bean и его зависимости каждый раз проинжектятся
 (т.е. Date каждый раз будет актуальная, а не та, что была при создании контекста). По умолчанию scope="singletone"
 - каждый раз при запросе бина у контейнера будет возвращать один и тот же бин, который создал при инициализаци
 */
@Component
@Scope("prototype")
public class Event {
private static final AtomicInteger AUTO_ID = new AtomicInteger();

    private int id;
    private String msg;

    @Autowired
    @Qualifier("newDate")//даже если не укажу имя бина из AppConfig, все равно найдет его по типу
    private Date date;

    @Autowired //находит бин по типу
    private DateFormat dateFormat;


    public Event() {
        this.id = AUTO_ID.getAndIncrement();
    }

    public Event(Date date, DateFormat dateFormat) {
        this();
        this.date = date;
        this.dateFormat = dateFormat;
    }

//    @PreDestroy //TODO 1 НЕ понимаю, почему не вызывается этот метод при закрытии контекста (если AUTO_ID сделать не final)
//    public void destroy(){
//        AUTO_ID = AUTO_ID;
//    }

    public int getId() {
        return id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", msg='" + msg + '\'' +
                ", date=" + (dateFormat != null ? dateFormat.format(date) : date) +
                '}';
    }
}
