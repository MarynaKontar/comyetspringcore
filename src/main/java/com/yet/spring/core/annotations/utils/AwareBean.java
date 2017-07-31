package com.yet.spring.core.annotations.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.*;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by User on 28.07.2017.
 * Aware интерфейсіы используются, когда классу в java надо знать,в каком контексте он находится или какое у него
 * имя в контексте. Реализовав Aware интерфейсы можно получить различную информацию от Spring во время инициализации бина.
 */
@Component
public class AwareBean implements ApplicationContextAware, BeanNameAware,
        ApplicationEventPublisherAware, BeanFactoryAware, ResourceLoaderAware {
    private ApplicationContext ctx;
    private String name;
    private ApplicationEventPublisher eventPublisher;

    private BeanFactory beanFactory;
    private ResourceLoader resourceLoader;

    @PostConstruct
    public void init() {
        System.out.println(this.getClass().getSimpleName() + " > My name is '"
                + name + "'");

        if (ctx != null)
            System.out.println(this.getClass().getSimpleName() + " > My context is '"
                    + ctx.getClass().toString() + "'");
        else System.out.println(this.getClass().getSimpleName() + " > Context is not set");

        if (eventPublisher != null)
            System.out.println(this.getClass().getSimpleName() + " > My eventPublisher is '"
                    + eventPublisher.getClass().toString() + "'");
        else System.out.println(this.getClass().getSimpleName() + " > EventPublisher is not set");

        if (beanFactory != null)
            System.out.println(this.getClass().getSimpleName() + " > My beanFactory is '"
                    + beanFactory.getClass().toString() + "'");
        else System.out.println(this.getClass().getSimpleName() + " > BeanFactory is not set");

        if (resourceLoader != null)
            System.out.println(this.getClass().getSimpleName() + " > My resourceLoader is '"
                    + resourceLoader.getClass().toString() + "'");
        else System.out.println(this.getClass().getSimpleName() + " > ResourceLoader is not set");

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

    @Override
    public void setBeanName(String name) {
        this.name = name;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
