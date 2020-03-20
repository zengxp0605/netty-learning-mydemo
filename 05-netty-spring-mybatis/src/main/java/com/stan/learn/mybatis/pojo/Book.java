package com.stan.learn.mybatis.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Author：stanzeng
 * @Description：
 * @Date ：Created in 2020/3/20 10:21 下午
 * @Modified By：
 */
@Data
@Accessors(chain = true)
public class Book {
    private Long id;
    private String bookName;
    private String author;
    private Date createTime;
}
