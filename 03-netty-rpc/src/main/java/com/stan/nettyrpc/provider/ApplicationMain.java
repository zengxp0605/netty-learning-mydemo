package com.stan.nettyrpc.provider;

import com.stan.nettyrpc.provider.server.MyNettyServer;

public class ApplicationMain {
    public static void main(String[] args) {
        MyNettyServer.startServer("localhost", 8088);
    }
}
