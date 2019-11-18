package com.example.demo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@ChannelHandler.Sharable
public class ExceptionHandler extends ChannelInboundHandlerAdapter {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.warn("Netty failed:", cause);
        if (ctx.channel().isActive()) {
            ByteBuf buffer = ctx.alloc().buffer();
            buffer.writeCharSequence(cause.getMessage(), StandardCharsets.UTF_8);
            ctx.writeAndFlush(buffer).addListener(ChannelFutureListener.CLOSE);
        }
    }
}
