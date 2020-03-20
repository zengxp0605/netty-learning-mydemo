package com.stan.learn.mybatis.repository;

import com.stan.learn.mybatis.config.DataSourceConfig;
import com.stan.learn.mybatis.pojo.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * @Author：stanzeng
 * @Description：
 * @Date ：Created in 2020/3/20 10:44 下午
 * @Modified By：
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataSourceConfig.class)
public class JdbcTemplateBookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void findBook() {
        Book book = bookRepository.findBook(1L);
        System.out.println(book);
    }
}