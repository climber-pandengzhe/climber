package com.climber.cloud_netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
    public static void main(String[] args) throws InterruptedException {

        //说明:
        //1:创建bossGroup 和 workerGroup
        //2:创建两个线程组,只是处理连接请求,真正的与客户端处理,会交给worekerGroup处理
        //3:两个都是无限循环
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            //创建服务器的启动对象,配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup)//设置两个线程组
                    .channel(NioServerSocketChannel.class)//使用NioServerSocketChannel作为服务器的通道
                    .option(ChannelOption.SO_BACKLOG,128)//设置线程队列,得到链接个数.
                    .childOption(ChannelOption.SO_KEEPALIVE,true)//设置保持活动链接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        //给pipeline设置chuliqi
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NettyServerHandler());
                        }
                    });//给我们的workerGroup的EventLoo对应的管道设置处理器.

            System.out.println("....服务器 is ready....");
            //绑定一个端口并同步,生成一个ChannelFuture对象
            //启动服务器(并绑定端口)
            ChannelFuture cf = bootstrap.bind(6668).sync();

            //给cf 注册监听器,监听我们关心的事件

            cf.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if(cf.isSuccess()){
                        System.out.println("监听端口6668成功");
                    }else{
                        System.out.println("失败:");
                    }
                }
            });
            //对关闭通道进行监听.
            cf.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }
}
