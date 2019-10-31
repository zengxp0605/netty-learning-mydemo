package com.stan.hellonetty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class MyServerChannel extends ChannelInitializer<SocketChannel> {

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline ph = ch.pipeline();
        //处理http服务的关键handler
        ph.addLast("encoder",new HttpResponseEncoder());
        ph.addLast("decoder",new HttpRequestDecoder());
        ph.addLast("aggregator", new HttpObjectAggregator(10*1024*1024));
        ph.addLast("handler", new MyHttpServerHandler());// 服务端业务逻辑

//        ch.pipeline().addLast(
//                new HttpResponseEncoder(),
//                new HttpRequestDecoder(),
//                new MyHttpServerHandler());
    }
}
