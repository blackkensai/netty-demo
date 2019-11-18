package com.example.demo;

import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.io.InputStream;

public class ByteBufInputStream extends InputStream {
    private ByteBuf byteBuf;

    public ByteBufInputStream(ByteBuf byteBuf) {
        this.byteBuf = byteBuf;
    }

    @Override
    public int read() throws IOException {
        try {
            return this.byteBuf.readByte();
        } catch (IndexOutOfBoundsException e) {
            return -1;
        }
    }
}
