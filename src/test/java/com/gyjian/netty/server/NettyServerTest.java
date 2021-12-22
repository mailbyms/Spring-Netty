package com.gyjian.netty.server;

import com.gyjian.netty.codec.MyMessageDecoder;
import com.gyjian.netty.codec.MyMessageEncoder;
import com.gyjian.netty.handler.MyClientHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.bootstrap.Bootstrap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;

import static org.junit.jupiter.api.Assertions.*;

class NettyServerTest {
    private String host = "127.0.0.1";
    private int port = 7000;

    @BeforeEach
    void setUp() {
        System.out.println("setup");
    }

    @AfterEach
    void tearDown() {
        System.out.println("tearDown");
    }

    @Test
    void startTest () throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class).remoteAddress(new InetSocketAddress(host, port))
                    .handler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast(new MyMessageDecoder());
                            ch.pipeline().addLast(new MyMessageEncoder());
                            ch.pipeline().addLast(new MyClientHandler());
                        }
                    });
            ChannelFuture f = b.connect().sync();

            //f.channel().closeFuture().sync();       // 这行会卡住

            Thread.sleep(2000); // 睡眠 2 秒，等待服务器返回的数据

        } finally {
            group.shutdownGracefully().sync();
        }
    }

}