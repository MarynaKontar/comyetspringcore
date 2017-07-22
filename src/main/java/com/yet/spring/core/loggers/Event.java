package com.yet.spring.core.loggers;

import java.text.DateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by User on 20.07.2017.
 */
public class Event {
    private int id;
    private String msg;
    private Date date;
    private DateFormat df;
    Random rn = new Random();

    public Event(Date date, DateFormat df) {
        this.id = rn.nextInt(100);
        this.date = date;
        this.df = df;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setDate(Date date) {
        this.date = date;
    }

    public DateFormat getDf() {
        return df;
    }

    public void setDf(DateFormat df) {
        this.df = df;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", msg='" + msg + '\'' +
                ", date=" + df.format(date) +
                '}';
    }
}
