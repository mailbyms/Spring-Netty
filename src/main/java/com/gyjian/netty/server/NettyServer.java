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
import java.util.HashSet;
import java.util.Set;

public class NettyServer {
    private final int port;
    private final int maxListenPortCount;
    private Logger log = LoggerFactory.getLogger(this.getClass());

    public NettyServer(int port, int maxListenPortCount)
    {
        this.port=port;
        this.maxListenPortCount = maxListenPortCount;
    }

    public void start() throws Exception{
        final MyServerHandler serverHandler = new MyServerHandler();
        EventLoopGroup group = new NioEventLoopGroup();
        try
        {
            ServerBootstrap serverBootstrap=new ServerBootstrap();
            serverBootstrap.group(group)
                    //.localAddress(new InetSocketAddress(port))
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline().addLast(new MyMessageDecoder());
                            channel.pipeline().addLast(new MyMessageEncoder());
                            channel.pipeline().addLast(serverHandler);
                        }
                    });

            Set<ChannelFuture> futureSet = new HashSet<>();
            for(int i = 0; i< maxListenPortCount; i++){
                int tmpPort = port + i;
                ChannelFuture f=serverBootstrap.bind(tmpPort).sync();
                log.info("LISTEN on port:{}", tmpPort);

                futureSet.add(f);
            }

            for(ChannelFuture f:futureSet){
                f.channel().closeFuture().sync();
            }

        }
        finally {
            group.shutdownGracefully().sync();
        }
    }
}
