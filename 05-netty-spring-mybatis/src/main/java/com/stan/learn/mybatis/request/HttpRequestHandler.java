package com.stan.learn.mybatis.request;

import com.alibaba.fastjson.JSONObject;
import com.stan.learn.mybatis.pojo.User;
import com.stan.learn.mybatis.service.UserService;
import io.netty.handler.codec.http.FullHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author：stanzeng
 * @Description：
 * @Date ：Created in 2020/3/20 9:26 下午
 * @Modified By：
 */
@Component
public class HttpRequestHandler implements RequestHandler {

    @Autowired
    private UserService userService;

    /**
     * TODO: 分发
     * @param request
     * @return
     */
    @Override
    public Object dispatch(FullHttpRequest request) {

        User user = userService.getUser();

        return JSONObject.toJSONString(user);
    }
}
