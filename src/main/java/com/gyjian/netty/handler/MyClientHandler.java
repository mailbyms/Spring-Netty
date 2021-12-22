package com.gyjian.netty.handler;

import com.gyjian.netty.model.MyMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 用在测试客户端
 * 生成测试消息，发送出去
 */

public class MyClientHandler extends SimpleChannelInboundHandler<MyMessage> {
    private int count;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // for(int i=0;i<10;i++){

        //    byte[] message = {0x10, 0x12, 0x15};
        //   int length = message.length;
        //   byte[] content = message;

        /* Scanner scanner = new Scanner(System.in);
         System.out.println("请输入:");
         String mess = scanner.nextLine();
         byte[] message = mess.getBytes();
         int length =message.length;
         byte[] content=message;*/

        for (int i = 0; i < 10; i++) {
            String messaage = "sent from client " + i;
            int length = messaage.getBytes(StandardCharsets.UTF_8).length;
            byte[] content = messaage.getBytes(StandardCharsets.UTF_8);

            MyMessage personProtocal = new MyMessage();
            personProtocal.setLength(length);
            personProtocal.setContent(content);
            ctx.writeAndFlush(personProtocal);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MyMessage msg) throws Exception {
        System.out.println("客户端接受到的数据：");
        System.out.println("数据长度:" + msg.getLength());
        System.out.println("数据内容：" + new String(msg.getContent(), StandardCharsets.UTF_8));
        System.out.println("客户端接收到的消息数量:" + (++count));
    }
}