package com.climber.cloud_netty.action;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 实现客户端发送请求,服务区返回hello netty
 */
public class HelloServer {
    public static void main(String[] args) {


        //定义主线程组
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        //定义从线程组
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //创建netty服务器,辅助工具类,用于服务器通道的系列配置,链式配置
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //绑定线程组
            serverBootstrap.group(bossGroup,workerGroup)
                    //指定NIO模式
                    .channel(NioServerSocketChannel.class)
                    //指定子处理器,处理workerGroup的工作
                    .childHandler(new HelloServerInitializer());

            //启动server,绑定端口,启动方式设定为同步
            ChannelFuture channelFuture = serverBootstrap.bind(8088).sync();
            //监听关闭的channel,设置为同步方式
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }
}
