package com.yet.spring.core.xml;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 *Єтот класс используется для того , чтобы следить за событиями контекста (создание, завершение, обновление, остановка)
 */


public class Monitor implements ApplicationListener {
    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {

    }
}
