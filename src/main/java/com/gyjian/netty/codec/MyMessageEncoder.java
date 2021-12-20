package com.gyjian.netty.codec;

import com.gyjian.netty.model.MyMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

//该类只有长度+内容
public class MyMessageEncoder extends MessageToByteEncoder<MyMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, MyMessage msg, ByteBuf out) throws Exception {
        System.out.println("MyPersonEncoder encode invoked");

        out.writeInt(msg.getLength());
        out.writeBytes(msg.getContent());
    }
}