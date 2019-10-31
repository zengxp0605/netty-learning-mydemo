package com.stan.hellonetty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class MyHttpServer {

    public static void main(String[] args) {
        int port = 2000;
        new MyHttpServer().start(port);
        System.out.println("Starting: " + port);
    }

    public void start(int port){
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();

        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                      @Override
                      public void initChannel(SocketChannel ch) throws Exception {
                          ch.pipeline().addLast(
                                  new HttpResponseEncoder(),
                                  new HttpRequestDecoder(),
                                  new MyHttpServerHandler());
                      }
                  }).option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);

        try {
            System.out.println("Server start at port: " + port);
            ChannelFuture channelFuture =bootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
