package com.stan.learn.mybatis.request;

import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @Author：stanzeng
 * @Description：
 * @Date ：Created in 2020/3/20 9:26 下午
 * @Modified By：
 */
public interface RequestHandler {
    Object dispatch(FullHttpRequest request);
}
