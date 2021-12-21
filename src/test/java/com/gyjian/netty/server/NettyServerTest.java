package com.gyjian.netty.server;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NettyServerTest {

    @BeforeEach
    void setUp() {
        System.out.println("setUp");
        int a = 1;
        assertEquals(a, 0);
    }

    @AfterEach
    void tearDown() {
        System.out.println("tearDown");
    }

    @Test
    void start() {
        System.out.println("hello");
    }
}