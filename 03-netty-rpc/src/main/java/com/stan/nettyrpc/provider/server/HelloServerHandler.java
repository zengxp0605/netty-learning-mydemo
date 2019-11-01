package com.stan.nettyrpc.provider.server;

import com.stan.nettyrpc.provider.service.impl.HelloServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class HelloServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{
        String providerName = "HelloService#hello#";
        System.out.println("channelRead: " + msg);
        /**
         * 这里显示判断了是否符合约定（并没有使用复杂的协议，只是一个字符串判断），然后创建一个具体实现类，并调用方法写回客户端。
         */
        if(msg.toString().startsWith(providerName)){
            int startIdx = msg.toString().lastIndexOf("#") + 1;
            String result = new HelloServiceImpl().hello(
                    msg.toString().substring(startIdx)
            );

            ctx.writeAndFlush(result);
        }
    }
}
