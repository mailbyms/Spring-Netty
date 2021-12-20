package com.gyjian.netty.server;

import com.gyjian.netty.codec.MyMessageDecoder;
import com.gyjian.netty.codec.MyMessageEncoder;
import com.gyjian.netty.handler.MyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public class NettyServer {
    private final int port;
    private Logger log = LoggerFactory.getLogger(this.getClass());

    public NettyServer(int port)
    {
        this.port=port;
    }

    public void start() throws Exception{
        final MyServerHandler serverHandler = new MyServerHandler();
        EventLoopGroup group = new NioEventLoopGroup();
        try
        {
            ServerBootstrap serverBootstrap=new ServerBootstrap();
            serverBootstrap.group(group)
                    .localAddress(new InetSocketAddress(port))
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline().addLast(new MyMessageDecoder());
                            channel.pipeline().addLast(new MyMessageEncoder());
                            channel.pipeline().addLast(serverHandler);
                        }
                    });
            ChannelFuture f=serverBootstrap.bind().sync();
            f.channel().closeFuture().sync();
        }
        finally {
            group.shutdownGracefully().sync();
        }
    }
}
