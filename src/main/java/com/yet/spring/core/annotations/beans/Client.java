package com.yet.spring.core.annotations.beans;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by User on 19.07.2017.
 * //Я могу в классе Client вообще не писать никаких @, а задать в классе с @Configuration (AppConfig) bean над методом,
 // к-ый возвращает client. Но для этого надо создать объект Environment environment. Если параметры для client
 // прописаны в классе свойств, то еще надо указать файл этих свойств @PropertySource("classpath:client.properties")
 */
@Component
@PropertySource("classpath:client.properties")
public class Client {
    private String id;
    private String fullName;
    private String greeting;

    public Client() {
    }

    public Client(String id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

}
