package com.stan.learn.mybatis.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Author：stanzeng
 * @Description：
 * @Date ：Created in 2020/3/20 2:20 下午
 * @Modified By：
 */
@Data
@Accessors(chain = true)
public class User {
    private Long id;

    private String userName;

    private Date date;
}
