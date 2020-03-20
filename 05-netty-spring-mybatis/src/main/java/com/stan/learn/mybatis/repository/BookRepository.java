package com.stan.learn.mybatis.repository;

import com.stan.learn.mybatis.pojo.Book;

/**
 * @Author：stanzeng
 * @Description：
 * @Date ：Created in 2020/3/20 10:23 下午
 * @Modified By：
 */
public interface BookRepository {
    void addBook(Book book);

    Book findBook(Long id);
}
