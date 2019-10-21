package com.zhibo.sanjilienv.comm;

import com.zhibo.sanjilienv.data.Config;
import com.zhibo.sanjilienv.util.SpringUtil;
import com.zhibo.sanjilienv.util.Util;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class Client {

    private static Logger logger = LoggerFactory.getLogger(Client.class);

    Config config = SpringUtil.getBean(Config.class);

    private NioEventLoopGroup workGroup = new NioEventLoopGroup(4);
    private static Channel channel;
    private Bootstrap bootstrap;

    public static void main(String[] args) throws Exception {
        Client client = new Client();
        client.start();
    }

    public static void sendData(byte[] bytes){
        if (channel == null || !channel.isActive()) {
            return;
        }
        ByteBuf buf = channel.alloc().buffer(bytes.length);
        buf.writeBytes(bytes);
        channel.writeAndFlush(buf);
        logger.info("send data: " + Util.bytesToHexString(bytes));
    }

    public void start() {
        try {
            bootstrap = new Bootstrap();
            bootstrap
                    .group(workGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline p = socketChannel.pipeline();
                            p.addLast(new ClientHandler(Client.this));
                        }
                    });
            doConnect();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void doConnect() {
        if (channel != null && channel.isActive()) {
            return;
        }

        logger.info("Connect to: ip = " + config.getTelecomServerName() + ", port = " + config.getTelecomServerPort());
        ChannelFuture future = bootstrap.connect(config.getTelecomServerName(), config.getTelecomServerPort());

        future.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture futureListener) throws Exception {
                if (futureListener.isSuccess()) {
                    channel = futureListener.channel();
                    logger.info("Connect to server successfully!");
                } else {
                    logger.error("Failed to connect to server, try connect after 10s");
                    futureListener.channel().eventLoop().schedule(new Runnable() {
                        @Override
                        public void run() {
                            doConnect();
                        }
                    }, 10, TimeUnit.SECONDS);
                }
            }
        });
    }
}
