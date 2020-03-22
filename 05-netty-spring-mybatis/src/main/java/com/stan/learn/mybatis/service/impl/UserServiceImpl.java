package com.stan.learn.mybatis.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.stan.learn.mybatis.pojo.Book;
import com.stan.learn.mybatis.pojo.User;
import com.stan.learn.mybatis.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Random;

/**
 * @Author：stanzeng
 * @Description：
 * @Date ：Created in 2020/3/20 5:22 下午
 * @Modified By：
 */

@Service("userService")
public class UserServiceImpl implements UserService {

    /**
     * TODO: 数据库连接
     * @return
     */
    @Override
    public User getUser() {
        User user = new User().setId(new Random().nextLong())
                .setUserName("Stan")
                .setDate(new Date());


        return user;
    }

}
