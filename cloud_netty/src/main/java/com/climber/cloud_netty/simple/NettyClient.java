package com.climber.cloud_netty.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        //只需要一个事件轮训组:
        EventLoopGroup eventExecutors = new NioEventLoopGroup();
       try {

           //创建一个客户端启动对象
           Bootstrap bootstrap = new Bootstrap();
           //相关参数设置:
           bootstrap.group(eventExecutors)
                   .channel(NioSocketChannel.class)//设置客户端通道的实现类(反射)
                   .handler(new ChannelInitializer<SocketChannel>() {
                       @Override
                       protected void initChannel(SocketChannel socketChannel) throws Exception {
                           socketChannel.pipeline().addLast(new NettyClientHandler());//加入自己的处理器
                       }
                   });
           System.out.println("--客户端 is ok--");
           //启动客户端连接服务器端
           //关于ChannelFuture 要分析,涉及到netty的异步模型.
           ChannelFuture sync = bootstrap.connect("127.0.0.1", 6668).sync();
           //关闭通道进行监听.
           sync.channel().closeFuture().sync();
       }finally {
           eventExecutors.shutdownGracefully();
       }
    }
}
