package com.stan.learn.mybatis.netty;

import com.stan.learn.mybatis.Application;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @Author：stanzeng
 * @Description：
 * @Date ：Created in 2020/3/20 9:19 下午
 * @Modified By：
 */
@Service
public class HttpServer {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    private static final int portNumber = 2001;

    @Autowired
    private HttpServerInitializer httpServerInitializer;

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("server.xml");
    }

    @PostConstruct
    public void init() {
        logger.info("Netty HttpServer will start");
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();

        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
//                .childHandler(new HttpServerInitializer()) //为新的连接创建新的channel
                .childHandler(httpServerInitializer) //为新的连接创建新的channel
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true);

        try {
            // 服务器绑定端口监听
            ChannelFuture channelFuture = bootstrap.bind(portNumber).sync();
            logger.info("Netty HttpServer started on port: {}", portNumber);

//            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            workerGroup.shutdownGracefully();
//            bossGroup.shutdownGracefully();
        }
    }
}
