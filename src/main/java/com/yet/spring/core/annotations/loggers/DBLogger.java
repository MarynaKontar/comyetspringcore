package com.yet.spring.core.annotations.loggers;

import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;

/**
 * Created by User on 01.08.2017.
 */
public class DBLogger implements EventLogger {
    JdbcTemplate jdbcTemplate;
    @Override
    public void logEvent(Event event) throws IOException {

    }
}
