package com.stan.learn.mybatis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author：stanzeng
 * @Description：
 * @Date ：Created in 2020/3/20 1:58 下午
 * @Modified By：
 */
public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        // 加载spring配置
        ApplicationContext context = new ClassPathXmlApplicationContext("server.xml");
//       new AnnotationConfigApplicationContext("com.stan.learn.mybatis");
        logger.info("Spring start...");
    }
}
