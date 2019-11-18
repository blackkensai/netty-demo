package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.StandardCharsets;

public class JsonEncoder extends MessageToByteEncoder<Response> {
    private ObjectMapper objectMapper = new ObjectMapper();

    public JsonEncoder() {
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Response msg, ByteBuf out) throws Exception {
        out.writeCharSequence(objectMapper.writeValueAsString(msg), StandardCharsets.UTF_8);
    }
}
