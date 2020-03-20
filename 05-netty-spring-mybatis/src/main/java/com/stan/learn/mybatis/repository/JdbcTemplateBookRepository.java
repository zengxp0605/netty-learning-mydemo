package com.stan.learn.mybatis.repository;

import com.stan.learn.mybatis.pojo.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author：stanzeng
 * @Description：
 * @Date ：Created in 2020/3/20 10:38 下午
 * @Modified By：
 */
@Repository
public class JdbcTemplateBookRepository implements BookRepository{
    private static final String SQL_SELECT_BOOK =
            "SELECT id,book_name,author,create_time FROM book WHERE id = ?;";

    @Autowired
    private JdbcOperations jdbcOperations;

    @Override
    public Book findBook(Long id) {
        return jdbcOperations.queryForObject(SQL_SELECT_BOOK, new BookRowMapper(), id);
    }

    private static final class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            Book book = new Book();
            book.setId(resultSet.getLong("id"));
            book.setBookName(resultSet.getString("book_name"));
            book.setAuthor(resultSet.getString("author"));
            book.setCreateTime(resultSet.getTimestamp("create_time"));
            return book;
        }
    }

    @Override
    public void addBook(Book book) {

    }
}
