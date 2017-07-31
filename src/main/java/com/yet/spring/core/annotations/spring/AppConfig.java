package com.yet.spring.core.annotations.spring;

import com.yet.spring.core.annotations.beans.Client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by User on 27.07.2017.
 */
@Configuration
@PropertySource("classpath:client.properties")  //можно не писатьздесь, если в классе Client прописать все аннотации для бина
public class AppConfig {

    @Autowired
    private Environment environment;//можно не использовать, если в классе Client прописать все аннотации для бина

    @Bean
    public Date newDate() {
        return new Date();
    }

    @Bean
    public DateFormat dateFormat() {
        return DateFormat.getDateTimeInstance();
    }


    //Я могу в классе Client вообще не писать никаких @, а задать в классе с @Configuration (AppConfig) bean над методом,
    // к-ый возвращает client. Но для этого надо создать объект Environment environment. Если параметры для client
    // прописаны в классе свойств, то еще надо указать файл этих свойств @PropertySource("classpath:client.properties").
    //можно не писать здесь этот бин, если в классе Client прописать все аннотации для бина
    @Bean
    public Client client() {
        Client client = new Client();
        client.setId(environment.getRequiredProperty("id"));//если значение не найдено в client.properties, то кинет IllegalStateException
        client.setFullName(environment.getRequiredProperty("name"));
        client.setGreeting(environment.getProperty("greeting"));//если значение не найдено в client.properties, то ветнет значение по умолчанию
        return client;
    }
}
