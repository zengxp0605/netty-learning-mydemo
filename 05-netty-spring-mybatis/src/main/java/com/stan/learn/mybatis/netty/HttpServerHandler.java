package com.stan.learn.mybatis.netty;

import com.stan.learn.mybatis.request.RequestHandler;
import com.stan.learn.mybatis.service.UserService;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.AsciiString;
import io.netty.util.CharsetUtil;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.Charsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author：stanzeng
 * @Description：
 * @Date ：Created in 2020/3/20 2:03 下午
 * @Modified By：
 */
@Service
@ChannelHandler.Sharable
public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    private static final Logger logger = LoggerFactory.getLogger(HttpServerHandler.class);

    private HttpHeaders headers;
    //    private HttpRequest request;
    private FullHttpRequest request;

    private static final String FAVICON_ICO = "/favicon.ico";
    private static final AsciiString CONTENT_TYPE = AsciiString.cached("Content-Type");
    private static final AsciiString CONTENT_LENGTH = AsciiString.cached("Content-Length");
    private static final AsciiString CONNECTION = AsciiString.cached("Connection");
    private static final AsciiString KEEP_ALIVE = AsciiString.cached("keep-alive");

    @Autowired
    private RequestHandler requestHandler;

    HttpServerHandler() {
        super();
        System.out.println("HttpServerHandler----constructor");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if (!(msg instanceof FullHttpRequest)) {
            logger.error("msg not instanceof HttpRequest");
            return;
        }

        request = (FullHttpRequest) msg;
        headers = request.headers();
        String uri = request.uri();
        if (FAVICON_ICO.equals(uri)) {
            return;
        }

        String content;
        HttpMethod method = request.method();
        logger.info("http uri: {}, method: {}", uri, method);
        if (HttpMethod.GET.equals(method)) {
            logger.info("http method GET");
            QueryStringDecoder queryStringDecoder = new QueryStringDecoder(uri, Charsets.toCharset(CharEncoding.UTF_8));
            Map<String, List<String>> uriAttributes = queryStringDecoder.parameters();
            for (Map.Entry<String, List<String>> attr : uriAttributes.entrySet()) {
                for (String val : attr.getValue()) {
                    logger.info("uriAttribute-> [{}]={}", attr.getKey(), val);
                }
            }

            content = "test get response";
        } else if (HttpMethod.POST.equals(method)) {
            // TODO: post body处理

            logger.info("http method POST");
            content = "test post response";
        } else {
            logger.error("http method other: {}", method);
            content = "other: " + method;
        }

        System.out.println(content);
        content = (String) requestHandler.dispatch(request);

        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK, Unpooled.copiedBuffer(content, CharsetUtil.UTF_8));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");

        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);

        // TODO：keepAlive怎么返回？？？
//
//        boolean keepAlive = HttpUtil.isKeepAlive(request);
//        logger.info("keepAlive: {}", keepAlive);
//        if (!keepAlive) {
//            ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
//        } else {
//            response.headers().set(CONNECTION, KEEP_ALIVE);
//            ctx.writeAndFlush(response);
//        }
    }

    /*
     * 建立连接时，返回消息
     */
//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        logger.info("连接的客户端地址:" + ctx.channel().remoteAddress());
//        ctx.writeAndFlush("客户端"+ InetAddress.getLocalHost().getHostName() + "成功与服务端建立连接！ ");
//        super.channelActive(ctx);
//    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }
}
