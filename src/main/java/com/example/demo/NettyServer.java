package com.example.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class NettyServer implements InitializingBean, DisposableBean {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${netty.server.port:8000}")
    private int port;

    @Autowired
    private NettyServerMessageAdaptor nettyServerMessageAdaptor;

    @Autowired
    private ExceptionHandler exceptionHandler;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private ChannelFuture channelFuture;
    private ServerBootstrap server;


    @Override
    public void afterPropertiesSet() throws Exception {
        this.run();
    }

    private void run() throws Exception {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();

        server = new ServerBootstrap();
        server.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new JsonDecoder(), new JsonEncoder()).addLast(nettyServerMessageAdaptor).addLast(exceptionHandler);
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128)
//                    .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_REUSEADDR, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true);

        channelFuture = server.bind(port).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                logger.info("Netty server listened on port {}.", port);
            }
        });


    }

    @Override
    public void destroy() throws Exception {
        this.logger.info("Begin to destroy netty server.");
        channelFuture.channel().closeFuture().addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                workerGroup.shutdownGracefully();
                bossGroup.shutdownGracefully();
            }
        });
        channelFuture.channel().close();
    }
}
