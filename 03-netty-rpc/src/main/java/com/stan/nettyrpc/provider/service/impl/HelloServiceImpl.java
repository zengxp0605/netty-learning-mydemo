package com.stan.nettyrpc.provider.service.impl;

import com.stan.nettyrpc.publicinterface.HelloService;

public class HelloServiceImpl implements HelloService {
    public String hello(String msg) {
        return msg != null ? msg + " -----> I am fine." : "I am fine.";
    }
}
