package com.stan.test;


import io.netty.bootstrap.ServerBootstrap;

public class HelloWorld {

    public static void main(String[] args) {
        ServerBootstrap bootstrap = new ServerBootstrap();
        System.out.println("===========" + bootstrap);
        new HelloWorld().start("from main");
    }

    public String start(String str){
        System.out.println("start...." + str);
        return "start..";
    }
}
