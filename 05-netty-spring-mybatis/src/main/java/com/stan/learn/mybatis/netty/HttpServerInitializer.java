package com.stan.learn.mybatis.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author：stanzeng
 * @Description：
 * @Date ：Created in 2020/3/20 2:02 下午
 * @Modified By：
 */
@Service
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

    @Autowired
    private HttpServerHandler httpServerHandler;

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline ph = ch.pipeline();
        //处理http服务的关键handler
        ph.addLast("encoder",new HttpResponseEncoder());
        ph.addLast("decoder",new HttpRequestDecoder());

        /**
         * 在处理POST消息体时需要加上
         */
        ph.addLast("aggregator", new HttpObjectAggregator(10*1024*1024));
        ph.addLast("handler", httpServerHandler);// 服务端业务逻辑

    }
}

