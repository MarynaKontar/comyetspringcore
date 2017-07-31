package com.yet.spring.core.xml.beans;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by User on 19.07.2017.
 */
public class Client implements ApplicationContextAware{
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

    // implements ApplicationContextAware нужно для того,чтобы получать информацию от спринга во время инициализации
    // этого бина. Можно implements ApplicationEventPublisherAware, BeanFactoryAware,BeanNameAware, ResourceLoaderAware...
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }
}
