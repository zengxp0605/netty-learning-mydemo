package com.stan.learn.mybatis.repository;

import com.stan.learn.mybatis.pojo.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author：stanzeng
 * @Description：
 * @Date ：Created in 2020/3/20 10:24 下午
 * @Modified By：
 */
//@Repository
public class JdbcBookRepository implements BookRepository {
    private static final String SQL_INSERT_BOOK =
            "INSERT INTO book(book_name, author, create_time) VALUES (?,?,?);";

    @Autowired
    private DataSource dataSource;

    @Override
    public void addBook(Book book) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());

            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT_BOOK);
            preparedStatement.setString(1, book.getBookName());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setTimestamp(3, new Timestamp(calendar.getTimeInMillis()));

            preparedStatement.execute();
        } catch (SQLException e) {
            // 异常处理相关代码
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // 异常处理相关代码
            }
        }
    }

    @Override
    public Book findBook(Long id) {
        return null;
    }
}
