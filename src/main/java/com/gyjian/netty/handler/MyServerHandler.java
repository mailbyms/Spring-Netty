package com.gyjian.netty.handler;

import com.gyjian.netty.model.MyMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;

@ChannelHandler.Sharable   //没写这个，当服务端关闭再连接时会报错Failed to initialize a channel. Closing
public class MyServerHandler extends SimpleChannelInboundHandler<MyMessage> {
    private int count;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyMessage msg) throws Exception {
        System.out.println("服务端接受到的数据：");
        System.out.println("数据长度:"+msg.getLength());
        System.out.println("数据内容："+ new String(msg.getContent(), Charset.forName("utf-8")) );
        System.out.println("服务端接收到的消息数量:"+(++count));

        String responseMessage = UUID.randomUUID().toString();
        int responseLength = responseMessage.getBytes(Charset.forName("utf-8")).length;
        byte[] responseContent = responseMessage.getBytes(Charset.forName("utf-8"));

        MyMessage message = new MyMessage();
        message.setLength(responseLength);
        message.setContent(responseContent);
        ctx.writeAndFlush(message);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
