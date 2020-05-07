package com.climber.cloud_netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * 自定义一个handler,需要继承netty规定好的某个handler适配器.
 * 这时,我们自定义一个handler,才能称为一个handler
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {


    /**
     * 通道就绪,触发该方法
     * @param ctx ChannelHandlerContext ctx:上下文对象 含有管道pipeline,通道
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client:"+ctx);
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,server:",CharsetUtil.UTF_8));
    }


    /**
     * 读取事件,会触发(这里我们可以读取客户端发送的消息)
     * @param ctx ChannelHandlerContext ctx:上下文对象 含有管道pipeline,通道
     * @param msg 客户端发送的数据,默认是Object
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server ctx="+ctx);
        System.out.println(msg.toString());
        //将msg转成 ByteBuf,Netty提供,非NIO中的ByteBuffer.
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("从服务器端收到的送消息是:"+byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("服务器地址是:"+ctx.channel().remoteAddress());

    }

    /**
     * 数据读取完毕,消息回复.
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //write+flush 将数据写入到缓冲,并刷新.
        //一般讲,我们要对数据进行编码
     //   ctx.writeAndFlush(Unpooled.copiedBuffer("hello channelReadComplete",CharsetUtil.UTF_8));
        //待会试试直接写channel.
    }
    /**
     * 出现异常,关闭通道
     * @param ctx
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
