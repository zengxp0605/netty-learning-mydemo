package com.stan.hellonetty;


import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

public class MyHttpServerHandler extends ChannelInboundHandlerAdapter {
    public MyHttpServerHandler() {
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channelRead");
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,
                Unpooled.wrappedBuffer("欢迎来到第一个demo".getBytes("utf-8")));

        response.headers().set("Content-Type", "text/plain;charset=UTF-8");
        response.headers().set("Content-Length", response.content().readableBytes());
        response.headers().set("Connection", "keep-alive");

        ctx.write(response);
        ctx.flush();
    }

    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelReadComplete");
        ctx.flush();
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exceptionCaught");
        ctx.close();
        cause.printStackTrace();
    }
}
