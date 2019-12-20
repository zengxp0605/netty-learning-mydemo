package stan.livedemo.server;

import io.netty.channel.*;
import io.netty.util.concurrent.ScheduledFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@ChannelHandler.Sharable
public class ServerHandler extends SimpleChannelInboundHandler<String> {
    private static Map<Integer, LiveChannelCache> channelCache = new HashMap<>();
    private Logger logger = LoggerFactory.getLogger(ServerHandler.class);

    /**
     * 建立连接时，发送一条庆祝消息
     * - 缓存 channel
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        final Channel channel = ctx.channel();
        final int hashCode = channel.hashCode();

        // 定时关闭链接??
        ScheduledFuture scheduledFuture = ctx.executor().schedule(() -> {
            logger.info("schedule runs, close channel:" + hashCode);
            channel.close();
            channelCache.remove(hashCode);
        }, 60, TimeUnit.SECONDS);

        channelCache.put(hashCode, new LiveChannelCache(channel, scheduledFuture));

        // 为新连接发送庆祝
        ctx.write("Welcome to " + InetAddress.getLocalHost().getHostName() + ",hashCode: " + hashCode + "!\r\n");
        ctx.write("It is " + new Date() + " now.\r\n");
        ctx.flush();
    }

    //业务逻辑处理
    @Override
    public void channelRead0(ChannelHandlerContext ctx, String request) throws Exception {
        // Generate and write a response.
        final int currentHashCode = ctx.channel().hashCode();
        String response;
        boolean close = false;
        if (request.isEmpty()) {
            response = "Please type something.\r\n";
            return;
        } else if ("bye".equals(request.toLowerCase())) {
            response = "Have a good day!\r\n";
            close = true;
        } else {
            response = currentHashCode + " say: '" + request + "'?";
        }

//        ChannelFuture future = ctx.write(response);
        // 发送给所有channel
        channelCache.entrySet().forEach(entry-> {
            Channel ch = entry.getValue().getChannel();
            ch.writeAndFlush(response + " --->Your hashCode: " + ch.hashCode() + "\r\n");

            logger.debug("currentHashCode:" + currentHashCode);
        });

//        if (close) {
//            future.addListener(ChannelFutureListener.CLOSE);
//        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    //异常处理
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}