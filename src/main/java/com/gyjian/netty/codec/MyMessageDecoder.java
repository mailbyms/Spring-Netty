package com.gyjian.netty.codec;

import com.gyjian.netty.model.MyMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * ByteToMessageDecoder 可以在阻塞的i/o模式下实现非阻塞的解码。
 * ReplayingDecoder 和 ByteToMessageDecoder 最大的不同就是 ReplayingDecoder ，不需要判断可用的字节
 * 继承了 ReplayingDecoder 无需关注粘包问题，将字节转换为协议实体 MyMessage
 */
public class MyMessageDecoder  extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyMessageDecoder decode invoked ");
        int length = in.readInt();
        byte[] content = new byte[length];
        in.readBytes(content);

        MyMessage msg = new MyMessage();
        msg.setLength(length);
        msg.setContent(content);

        out.add(msg);
    }
}