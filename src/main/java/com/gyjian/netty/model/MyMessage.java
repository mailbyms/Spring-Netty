package com.gyjian.netty.model;

/** 自定义消息结构，4字节消息长度 + 消息正文
 *
 */
public class MyMessage {

    private int length;
    private byte[] content;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}