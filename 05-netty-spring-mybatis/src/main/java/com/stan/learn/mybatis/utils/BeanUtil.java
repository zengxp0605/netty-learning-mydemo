package com.stan.learn.mybatis.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @Author：stanzeng
 * @Description：
 * @Date ：Created in 2020/3/20 5:25 下午
 * @Modified By：
 */
public class BeanUtil implements ApplicationContextAware {
    private static final Logger logger = LoggerFactory.getLogger(BeanUtil.class);

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("setApplicationContext ");
        BeanUtil.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> tClass){
        return applicationContext.getBean(tClass);
    }
}
