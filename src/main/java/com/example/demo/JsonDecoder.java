package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class JsonDecoder extends ByteToMessageDecoder {
    private ObjectMapper objectMapper = new ObjectMapper();

    public JsonDecoder() {
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        out.add(objectMapper.readValue(new ByteBufInputStream(in), Request.class));
    }

}
