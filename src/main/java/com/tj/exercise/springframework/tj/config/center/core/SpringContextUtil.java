package com.tj.exercise.springframework.tj.config.center.core;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Author: tj
 * @Date: 2022/10/29 19:36
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

    private static  ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> t){
        return applicationContext.getBean(t);
    }
}
