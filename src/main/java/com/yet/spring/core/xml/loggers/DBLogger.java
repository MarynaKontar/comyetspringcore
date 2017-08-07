package com.yet.spring.core.xml.loggers;

import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by User on 01.08.2017.
 */
public class DBLogger implements EventLogger {

    private JdbcTemplate jdbcTemplate;

    public void init() {
//        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS t_event(\n" +
//                "`id`INT(11) NOT NULL UNIQUE,\n" +
//                "`msg` VARCHAR(255) NOT NULL,\n" +
//                "`date` timestamp,\n" +
//                "PRIMARY KEY(`id`)\n" +
//                ")");
    }

    public void destroy() {
        try {
            jdbcTemplate.getDataSource().getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void logEvent(Event event) throws IOException {
//        Connection connection = null;
        try (
                Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            try {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO t_event (id, msg) VALUES (?,?)");
                ps.setInt(1, event.getId());
                ps.setString(2, event.getMsg());
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
//            jdbcTemplate.update("INSERT INTO t_event (id, msg) VALUES (?,?)", event.getId(), event.toString());
        }
    }


    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
