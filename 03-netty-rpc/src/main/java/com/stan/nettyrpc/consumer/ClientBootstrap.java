package com.stan.nettyrpc.consumer;

import com.stan.nettyrpc.consumer.RpcConsumer;
import com.stan.nettyrpc.publicinterface.HelloService;

public class ClientBootstrap {

    public static final String providerName = "HelloService#hello#";

    public static void main(String[] args) throws InterruptedException {

        RpcConsumer consumer = new RpcConsumer();
        // 创建一个代理对象
        HelloService service = (HelloService) consumer
                .createProxy(HelloService.class, providerName);
        for (; ; ) {
            Thread.sleep(2000);
            System.out.println(service.hello(System.currentTimeMillis() + ": are you ok ?"));
        }
    }
}
