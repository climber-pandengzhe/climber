package com.climber.cloud_netty.action;

import com.climber.cloud_netty.simple.NettyClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class HelloClient {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            // 注册线程池
            b.group(group)
                    // 使用NioSocketChannel来作为连接用的channel类
                    .channel(NioSocketChannel.class)
                    // 绑定连接端口和host信息
                    .remoteAddress(new InetSocketAddress("127.0.0.1", 8088))
                    // 绑定连接初始化器
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //这里放入自定义助手类
                            ch.pipeline().addLast(new NettyClientHandler());
                        }
                    });
            // 异步连接服务器
            ChannelFuture cf = b.connect().sync();

            System.out.println("--客户端 is ok--");

            // 异步等待关闭连接channel
            cf.channel().closeFuture().sync();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            group.shutdownGracefully().sync();
        }
    }

}
